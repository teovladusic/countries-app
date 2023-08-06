package com.puzzle_agency.countriesapp.presentation.country_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puzzle_agency.countriesapp.domain.result.Result
import com.puzzle_agency.countriesapp.domain.usecase.GetCountryByAlphaUseCase
import com.puzzle_agency.countriesapp.presentation.destinations.CountryDetailsScreenDestination
import com.puzzle_agency.countriesapp.presentation.navigation.NavigationEvent
import com.puzzle_agency.countriesapp.presentation.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCountryByAlphaUseCase: GetCountryByAlphaUseCase,
    private val navigationManager: NavigationManager
) : ViewModel() {


    private val _viewState = MutableStateFlow(CountryDetailsViewState())
    val viewState: StateFlow<CountryDetailsViewState> = _viewState

    init {
        val alpha = savedStateHandle.get<String>("alpha")

        alpha?.let { fetchCountry(it) }
    }

    private fun fetchCountry(alpha: String) = viewModelScope.launch {
        _viewState.update { it.copy(isLoading = true) }

        when (val result = getCountryByAlphaUseCase(alpha)) {
            is Result.Failure -> _viewState.update { it.copy(isLoading = false, isError = true) }

            is Result.Success -> _viewState.update {
                it.copy(isLoading = false, country = result.value)
            }
        }
    }

    fun onBorderCountryClick(alpha: String) {
        navigationManager.navigate(
            NavigationEvent(direction = CountryDetailsScreenDestination(alpha))
        )
    }

    fun dismissDialog() {
        _viewState.update { it.copy(isError = false) }
    }
}