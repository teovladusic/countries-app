package com.puzzle_agency.countriesapp.domain.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val imageUrl: String,
    val commonName: String,
    val flagUrl: String,
    val capitalCityName: String?,
    val populationCount: Long,
    val areaKm: Double,
    val regionName: String,
    val languages: List<String>,
    val borderCountries: List<String>,
    val timeZones: List<String>,
    val mapsUrl: String?,
    val officialName: String,
    val coatOfArmsImageUrl: String?,
    val ccn3: String
): Parcelable