package com.puzzle_agency.countriesapp.domain.usecase

import com.puzzle_agency.countriesapp.domain.home.model.Country
import com.puzzle_agency.countriesapp.domain.home.repository.CountryRepository
import com.puzzle_agency.countriesapp.domain.result.Result
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetCountryByAlphaUseCase @Inject constructor(
    private val countryRepository: CountryRepository
) {

    suspend operator fun invoke(alpha: String): Result<Country> {
        return countryRepository.getCountryByAlpha(alpha)
    }
}