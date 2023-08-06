package com.puzzle_agency.countriesapp.presentation.country_details

import com.puzzle_agency.countriesapp.domain.home.model.Country

data class CountryDetailsViewState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val country: Country? = null
)