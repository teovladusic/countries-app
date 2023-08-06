package com.puzzle_agency.countriesapp.data.datasource.api.model

import com.puzzle_agency.countriesapp.domain.home.model.Country

data class CountryApiModel(
    val name: NameApiModel?,
    val maps: MapsApiModel?,
    val flags: FlagsApiModel?,
    val population: Long?,
    val area: Double?,
    val region: String?,
    val languages: Map<String, String>?,
    val borders: List<String>?,
    val timezones: List<String>?,
    val capital: List<String>?,
    val coatOfArms: CoatOfArms?,
    val ccn3: String?
)

data class NameApiModel(
    val common: String?,
    val official: String?,
)

data class MapsApiModel(
    val googleMaps: String?,
)

data class FlagsApiModel(
    val png: String?,
)

data class CoatOfArms(
    val png: String?
)

fun CountryApiModel.toDomainModel() : Country {
    return Country(
        imageUrl = "https://a.cdn-hotels.com/gdcs/production55/d211/c6daccd0-06e0-4057-86d8-a88257d60db8.jpg",
        commonName = name?.common.orEmpty(),
        flagUrl = flags?.png.orEmpty(),
        capitalCityName = capital?.firstOrNull(),
        populationCount = population ?: 0,
        areaKm = area ?: 0.0,
        regionName = region.orEmpty(),
        languages = languages?.values.orEmpty().toList(),
        borderCountries = borders.orEmpty(),
        timeZones = timezones.orEmpty(),
        mapsUrl = maps?.googleMaps,
        officialName = name?.official.orEmpty(),
        coatOfArmsImageUrl = coatOfArms?.png,
        ccn3 = ccn3.orEmpty()
    )
}
