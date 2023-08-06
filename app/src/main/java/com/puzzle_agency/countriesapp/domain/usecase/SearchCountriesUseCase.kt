package com.puzzle_agency.countriesapp.domain.usecase

import com.puzzle_agency.countriesapp.domain.home.model.Country
import com.puzzle_agency.countriesapp.domain.home.repository.HomeRepository
import com.puzzle_agency.countriesapp.domain.result.Result
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SearchCountriesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend operator fun invoke(query: String): Result<List<Country>> {
        return homeRepository.searchCountry(query)
    }
}