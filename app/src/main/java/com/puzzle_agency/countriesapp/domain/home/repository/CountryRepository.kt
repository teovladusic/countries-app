package com.puzzle_agency.countriesapp.domain.home.repository

import com.puzzle_agency.countriesapp.domain.home.model.Country
import com.puzzle_agency.countriesapp.domain.result.Result

interface CountryRepository {

    suspend fun searchCountry(name: String): Result<List<Country>>

    suspend fun getCountryByAlpha(alpha: String): Result<Country>
}