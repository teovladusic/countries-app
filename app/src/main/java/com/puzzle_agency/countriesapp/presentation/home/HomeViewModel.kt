package com.puzzle_agency.countriesapp.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puzzle_agency.countriesapp.R
import com.puzzle_agency.countriesapp.domain.home.model.Country
import com.puzzle_agency.countriesapp.domain.result.Result
import com.puzzle_agency.countriesapp.domain.usecase.SearchCountriesUseCase
import com.puzzle_agency.countriesapp.presentation.destinations.CountryDetailsScreenDestination
import com.puzzle_agency.countriesapp.presentation.navigation.NavigationEvent
import com.puzzle_agency.countriesapp.presentation.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchCountriesUseCase: SearchCountriesUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager
) : ViewModel() {

    companion object {
        private const val SAVED_QUERY_KEY = "query"
    }

    private val _homeViewState =
        MutableStateFlow(HomeViewState(sortOptions = provideDefaultSortOptions()))

    val viewState: StateFlow<HomeViewState> = _homeViewState

    fun onQueryChanged(query: String = savedStateHandle[SAVED_QUERY_KEY] ?: "") {
        getCountries(query)
        _homeViewState.update { it.copy(searchQuery = query) }
    }

    private var job: Job? = null

    private fun getCountries(query: String) {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            if (query.isBlank()) {
                _homeViewState.update { it.copy(countries = emptyList(), loading = false) }
                return@launch
            }

            _homeViewState.update { it.copy(loading = true, countries = emptyList()) }

            when (val result = searchCountriesUseCase(query)) {
                is Result.Failure -> _homeViewState.update {
                    it.copy(error = true, loading = false)
                }

                is Result.Success -> _homeViewState.update {
                    it.copy(countries = result.value, loading = false)
                }
            }
        }
    }

    fun dismissError() {
        _homeViewState.update { it.copy(error = false) }
    }

    fun onSortOptionClick(type: SortOption.Type) {
        val selectedOption = _homeViewState.value.sortOptions.firstOrNull { it.selected }

        if (selectedOption?.type == type) return

        val newSortOptions = _homeViewState.value.sortOptions.map {
            it.copy(selected = it.type == type)
        }

        _homeViewState.update {
            it.copy(sortOptions = newSortOptions)
        }

        sortCountries(type, _homeViewState.value.sortDirection)
    }

    private fun sortCountries(type: SortOption.Type, direction: SortDirection) {
        val sorted = when (type) {
            SortOption.Type.ALPHABETICALLY -> sortByDirection(direction) { it.commonName }

            SortOption.Type.POPULATION -> sortByDirection(direction) { it.populationCount }

            SortOption.Type.AREA -> sortByDirection(direction) { it.areaKm }
        }

        viewModelScope.launch {
            //delay for 500 ms to get smoother ux
            _homeViewState.update { it.copy(countries = emptyList()) }
            delay(500)
            _homeViewState.update { it.copy(countries = sorted) }
        }
    }

    private fun <T : Comparable<T>> sortByDirection(
        direction: SortDirection,
        selector: (Country) -> T
    ): List<Country> {
        return when (direction) {
            SortDirection.ASCENDING ->
                _homeViewState.value.countries.sortedBy { selector(it) }

            SortDirection.DESCENDING ->
                _homeViewState.value.countries.sortedByDescending { selector(it) }
        }
    }


    private fun provideDefaultSortOptions() =
        listOf(
            SortOption(type = SortOption.Type.ALPHABETICALLY, R.string.alphabetically, false),
            SortOption(type = SortOption.Type.POPULATION, R.string.population, false),
            SortOption(type = SortOption.Type.AREA, R.string.area, false)
        )

    fun onSortDirectionClick() {
        val newDirection = when (_homeViewState.value.sortDirection) {
            SortDirection.ASCENDING -> SortDirection.DESCENDING
            SortDirection.DESCENDING -> SortDirection.ASCENDING
        }

        _homeViewState.update {
            it.copy(sortDirection = newDirection)
        }

        val sortOption = _homeViewState.value.sortOptions.firstOrNull { it.selected }
            ?: return

        sortCountries(sortOption.type, newDirection)
    }

    fun onCountryClick(country: Country) {
        navigationManager.navigate(
            NavigationEvent(direction = CountryDetailsScreenDestination(country.ccn3))
        )
    }
}