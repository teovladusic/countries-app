package com.puzzle_agency.countriesapp.presentation.country_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.puzzle_agency.countriesapp.domain.home.model.Country
import com.puzzle_agency.countriesapp.presentation.common.components.image.DefaultStateImage
import com.puzzle_agency.countriesapp.ui.theme.Dimens
import com.puzzle_agency.countriesapp.ui.theme.Typography
import com.puzzle_agency.countriesapp.ui.theme.a4TextStyle
import com.ramcosta.composedestinations.annotation.Destination


@Destination(navArgsDelegate = CountryDetailsScreenArgs::class)
@Composable
fun CountryDetailsScreen(
    viewModel: CountryDetailsViewModel = hiltViewModel()
) {

    val country by viewModel.country.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            country?.let { country ->
                DefaultStateImage(url = country.imageUrl, modifier = Modifier.fillMaxSize())

                CountryDetailsBox(
                    modifier = Modifier
                        .padding(
                            vertical = Dimens.spacing_triple,
                            horizontal = Dimens.spacing_normal
                        )
                        .align(Alignment.BottomCenter),
                    country = country
                )
            }
        }
    }
}

@Composable
private fun CountryDetailsBox(modifier: Modifier, country: Country) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0x4D1C172D), shape = RoundedCornerShape(size = 32.dp))
    ) {
        Spacer(modifier = Modifier.height(Dimens.spacing_double))

        Text(
            text = country.regionName,
            color = Color(0xFF716E7F),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.spacing_normal),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Dimens.spacing_double))

        Row(
            modifier = Modifier.padding(horizontal = Dimens.spacing_normal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = country.officialName,
                style = Typography.a4TextStyle,
                color = Color(0xFF058DF0)
            )

            Spacer(modifier = Modifier.width(Dimens.spacing_basic))

            AsyncImage(model = country.coatOfArmsImageUrl, contentDescription = null)
        }
    }
}

data class CountryDetailsScreenArgs(
    val country: Country
)