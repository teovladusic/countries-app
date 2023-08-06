package com.puzzle_agency.countriesapp.data

import com.google.gson.GsonBuilder
import com.puzzle_agency.countriesapp.BuildConfig
import com.puzzle_agency.countriesapp.data.datasource.api.service.CountryRetrofitService
import com.puzzle_agency.countriesapp.data.repository.CountryRepositoryImpl
import com.puzzle_agency.countriesapp.data.retrofit.ApiResultAdapterFactory
import com.puzzle_agency.countriesapp.domain.home.repository.CountryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory =
        GsonConverterFactory.create(GsonBuilder().serializeNulls().create())

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(ApiResultAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideCountryRetrofitService(retrofit: Retrofit): CountryRetrofitService =
        retrofit.create(CountryRetrofitService::class.java)


    @Provides
    @Singleton
    fun provideHomeRepository(countryRetrofitService: CountryRetrofitService): CountryRepository =
        CountryRepositoryImpl(countryRetrofitService)
}