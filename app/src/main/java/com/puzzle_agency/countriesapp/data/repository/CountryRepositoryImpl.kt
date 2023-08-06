package com.puzzle_agency.countriesapp.data.repository

import android.util.Log
import com.puzzle_agency.countriesapp.data.datasource.api.model.toDomainModel
import com.puzzle_agency.countriesapp.data.datasource.api.service.CountryRetrofitService
import com.puzzle_agency.countriesapp.data.retrofit.ApiResult
import com.puzzle_agency.countriesapp.domain.home.model.Country
import com.puzzle_agency.countriesapp.domain.home.repository.CountryRepository
import com.puzzle_agency.countriesapp.domain.result.Result
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    private val countryRetrofitService: CountryRetrofitService
) : CountryRepository {

    override suspend fun searchCountry(name: String): Result<List<Country>> =
        when (val apiResult = countryRetrofitService.searchCountry(name)) {
            is ApiResult.Error -> Result.Failure()

            is ApiResult.Exception -> {
                Log.d("HomeRepositoryImpl", "searchCountry: ${apiResult.throwable.message}")
                Result.Failure()
            }

            is ApiResult.Success -> Result.Success(apiResult.data.map { it.toDomainModel() })
        }

    override suspend fun getCountryByAlpha(alpha: String): Result<Country> =
        when (val apiResult = countryRetrofitService.getCountriesByAlpha(alpha)) {
            is ApiResult.Error -> Result.Failure()

            is ApiResult.Exception -> {
                Log.d("HomeRepositoryImpl", "getCountryByAlpha: ${apiResult.throwable.message}")
                Result.Failure()
            }

            is ApiResult.Success -> {
                val country = apiResult.data.firstOrNull()?.toDomainModel()

                if (country != null) Result.Success(country)
                else Result.Failure()
            }
        }
}