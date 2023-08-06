package com.puzzle_agency.countriesapp.presentation.home

import androidx.annotation.StringRes
import com.puzzle_agency.countriesapp.domain.home.model.Country

data class HomeViewState(
    val searchQuery: String = "",
    val countries: List<Country> = emptyList(),
    val loading: Boolean = false,
    val error: Boolean = false,
    val sortOptions: List<SortOption> = emptyList(),
    val sortDirection: SortDirection = SortDirection.ASCENDING
)

data class SortOption(
    val type: Type,
    @StringRes val nameResId: Int,
    val selected: Boolean
) {

    enum class Type {
        ALPHABETICALLY, POPULATION, AREA
    }
}

enum class SortDirection {
    ASCENDING, DESCENDING
}