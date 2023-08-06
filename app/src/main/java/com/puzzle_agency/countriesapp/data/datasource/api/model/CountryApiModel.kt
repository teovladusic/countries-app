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
    val timeZones: List<String>?,
    val capital: List<String>?,
    val coatOfArms: CoatOfArms?
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
        imageUrl = "https://www.planetware.com/wpimages/2019/09/croatia-in-pictures-most-beautiful-places-to-visit-vrbnik-town.jpg",
        commonName = name?.common.orEmpty(),
        flagUrl = flags?.png.orEmpty(),
        capitalCityName = capital?.firstOrNull(),
        populationCount = population ?: 0,
        areaKm = area ?: 0.0,
        regionName = region.orEmpty(),
        languages = languages?.values.orEmpty().toList(),
        borderCountries = borders.orEmpty(),
        timeZones = timeZones.orEmpty(),
        mapsUrl = maps?.googleMaps,
        officialName = name?.official.orEmpty(),
        coatOfArmsImageUrl = coatOfArms?.png
    )
}
