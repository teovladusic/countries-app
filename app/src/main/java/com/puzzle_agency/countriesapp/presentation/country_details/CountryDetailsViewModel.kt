package com.puzzle_agency.countriesapp.presentation.country_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.puzzle_agency.countriesapp.domain.home.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    val country = savedStateHandle.getStateFlow<Country?>("country", null)
}