package com.puzzle_agency.countriesapp.data.repository

import android.util.Log
import com.puzzle_agency.countriesapp.data.datasource.api.model.toDomainModel
import com.puzzle_agency.countriesapp.data.datasource.api.service.CountryRetrofitService
import com.puzzle_agency.countriesapp.data.retrofit.ApiResult
import com.puzzle_agency.countriesapp.domain.home.model.Country
import com.puzzle_agency.countriesapp.domain.home.repository.HomeRepository
import com.puzzle_agency.countriesapp.domain.result.Result
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val countryRetrofitService: CountryRetrofitService
) : HomeRepository {

    override suspend fun searchCountry(name: String): Result<List<Country>> =
        when (val apiResult = countryRetrofitService.searchCountry(name)) {
            is ApiResult.Error -> Result.Failure()

            is ApiResult.Exception -> {
                Log.d("HomeRepositoryImpl", "searchCountry: ${apiResult.throwable.message}")
                Result.Failure()
            }

            is ApiResult.Success -> Result.Success(apiResult.data.map { it.toDomainModel() })
        }
}