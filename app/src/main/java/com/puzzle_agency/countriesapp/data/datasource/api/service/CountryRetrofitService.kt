package com.puzzle_agency.countriesapp.data.datasource.api.service

import com.puzzle_agency.countriesapp.data.datasource.api.model.CountryApiModel
import com.puzzle_agency.countriesapp.data.retrofit.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryRetrofitService {

    @GET("name/{query}")
    suspend fun searchCountry(
        @Path("query") name: String
    ): ApiResult<List<CountryApiModel>>

    @GET("alpha/{alpha}")
    suspend fun getCountriesByAlpha(
        @Path("alpha") alpha: String
    ): ApiResult<List<CountryApiModel>>
}