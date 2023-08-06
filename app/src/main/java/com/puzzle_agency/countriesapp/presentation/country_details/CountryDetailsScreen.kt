package com.puzzle_agency.countriesapp.presentation.country_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.puzzle_agency.countriesapp.R
import com.puzzle_agency.countriesapp.domain.home.model.Country
import com.puzzle_agency.countriesapp.presentation.common.components.dialog.SimpleErrorDialog
import com.puzzle_agency.countriesapp.presentation.common.components.image.DefaultStateImage
import com.puzzle_agency.countriesapp.presentation.common.components.loading.ProgressIndicator
import com.puzzle_agency.countriesapp.presentation.common.openUrl
import com.puzzle_agency.countriesapp.ui.theme.ColorScheme
import com.puzzle_agency.countriesapp.ui.theme.Dimens
import com.puzzle_agency.countriesapp.ui.theme.Typography
import com.puzzle_agency.countriesapp.ui.theme.a3TextStyle
import com.puzzle_agency.countriesapp.ui.theme.a4RegularTextStyle
import com.puzzle_agency.countriesapp.ui.theme.b0RegularTextStyle
import com.puzzle_agency.countriesapp.ui.theme.b1MediumTextStyle
import com.ramcosta.composedestinations.annotation.Destination


@Destination(navArgsDelegate = CountryDetailsScreenArgs::class)
@Composable
fun CountryDetailsScreen(
    viewModel: CountryDetailsViewModel = hiltViewModel()
) {

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val country = viewState.country

    if (viewState.isError) SimpleErrorDialog(viewModel::dismissDialog)

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (viewState.isLoading) {
                ProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .zIndex(2f)
                )
            }

            country?.let { country ->
                DefaultStateImage(url = country.imageUrl, modifier = Modifier.fillMaxSize())

                CountryDetailsBox(
                    modifier = Modifier
                        .padding(
                            vertical = Dimens.spacing_triple,
                            horizontal = Dimens.spacing_normal
                        )
                        .align(Alignment.BottomCenter),
                    country = country,
                    onBorderCountryClick = viewModel::onBorderCountryClick
                )
            }
        }
    }
}

@Composable
private fun CountryDetailsBox(
    modifier: Modifier,
    country: Country,
    onBorderCountryClick: (String) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xD91C172D), shape = RoundedCornerShape(size = 32.dp))
            .padding(bottom = Dimens.spacing_double)
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

        OfficialName(country)

        Spacer(modifier = Modifier.height(Dimens.spacing_normal))

        Languages(languages = country.languages)

        BorderCountries(country.borderCountries, onBorderCountryClick)

        TimeZones(country.timeZones)

        country.mapsUrl?.let {
            Spacer(modifier = Modifier.height(Dimens.spacing_normal))

            GoogleMapsButton { context.openUrl(it) }
        }
    }
}

@Composable
private fun BorderCountries(borderCountries: List<String>, onBorderCountryClick: (String) -> Unit) {
    if (borderCountries.isNotEmpty()) {
        TitledHorizontalList(
            title = stringResource(id = R.string.border_countries),
            items = {
                items(borderCountries) {
                    OutlinedButton(
                        onClick = { onBorderCountryClick(it) },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = ColorScheme.primary
                        ),
                        border = BorderStroke(0.dp, Color.Transparent)
                    ) { Text(text = it, color = Color.White) }
                }
            }
        )

        Spacer(modifier = Modifier.height(Dimens.spacing_normal))
    }

}

@Composable
private fun TimeZones(timeZones: List<String>) {
    if (timeZones.isNotEmpty()) {
        TitledHorizontalList(
            title = stringResource(id = R.string.time_zones),
            items = {
                items(timeZones) { TextListItem(text = it) }
            }
        )

        Spacer(modifier = Modifier.height(Dimens.spacing_normal))
    }
}

@Composable
private fun Languages(languages: List<String>) {
    if (languages.isNotEmpty()) {
        TitledHorizontalList(
            title = stringResource(id = R.string.official_languages),
            items = {
                items(languages) { TextListItem(text = it) }
            }
        )

        Spacer(modifier = Modifier.height(Dimens.spacing_normal))
    }
}

@Composable
private fun OfficialName(country: Country) {
    Row(
        modifier = Modifier.padding(horizontal = Dimens.spacing_normal),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = country.officialName,
            style = Typography.a4RegularTextStyle,
            color = Color(0xFF058DF0)
        )

        Spacer(modifier = Modifier.width(Dimens.spacing_basic))

        AsyncImage(
            model = country.coatOfArmsImageUrl, contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun TitledHorizontalList(title: String, items: LazyListScope.() -> Unit) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = Typography.b1MediumTextStyle,
            color = Color(0xFF716E7F),
            modifier = Modifier.padding(horizontal = Dimens.spacing_normal)
        )

        Spacer(modifier = Modifier.height(Dimens.spacing_basic))

        LazyRow(
            contentPadding = PaddingValues(horizontal = Dimens.spacing_normal),
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacing_normal_semi)
        ) { items() }
    }
}

@Composable
private fun TextListItem(text: String) {
    Text(text = text, color = Color.White, style = Typography.a3TextStyle)
}

@Composable
private fun GoogleMapsButton(onClick: () -> Unit) {
    FilledTonalButton(
        onClick = onClick,
        modifier = Modifier
            .height(44.dp)
            .padding(horizontal = Dimens.spacing_normal)
            .fillMaxWidth(),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = ColorScheme.primary
        ),
        shape = RoundedCornerShape(100.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.google_maps),
                style = Typography.b0RegularTextStyle,
                color = Color.White
            )

            Spacer(modifier = Modifier.width(Dimens.spacing_basic))

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

data class CountryDetailsScreenArgs(
    val alpha: String
)