package com.puzzle_agency.countriesapp.presentation.home

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.puzzle_agency.countriesapp.R
import com.puzzle_agency.countriesapp.domain.home.model.Country
import com.puzzle_agency.countriesapp.presentation.common.components.dialog.SimpleErrorDialog
import com.puzzle_agency.countriesapp.presentation.common.components.image.DefaultStateImage
import com.puzzle_agency.countriesapp.presentation.common.components.loading.ProgressIndicator
import com.puzzle_agency.countriesapp.presentation.common.offsetForPage
import com.puzzle_agency.countriesapp.ui.theme.ColorScheme
import com.puzzle_agency.countriesapp.ui.theme.Dimens
import com.puzzle_agency.countriesapp.ui.theme.Typography
import com.puzzle_agency.countriesapp.ui.theme.a4TextStyle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlin.math.absoluteValue
import kotlin.math.min

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    val lifecycleOwner = LocalLifecycleOwner.current
    var lifecycleEvent by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }

    DisposableEffect(key1 = lifecycleOwner) {
        val lifecycleEventObserver = LifecycleEventObserver { _, event ->
            lifecycleEvent = event
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleEventObserver)
        }
    }

    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_CREATE) {
            viewModel.onQueryChanged("hr")
        }
    }

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    if (viewState.error) SimpleErrorDialog(viewModel::dismissError)

    Scaffold(
        modifier = Modifier
            .background(Color(0xFF1C172D))
            .fillMaxSize()
    ) { paddingValues ->
        if (viewState.loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(2f)
            ) {
                ProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color(0xFF1C172D))
                .padding(bottom = Dimens.spacing_double_semi)
        ) {
            Spacer(modifier = Modifier.height(Dimens.spacing_triple_semi))

            Header()

            Spacer(modifier = Modifier.height(Dimens.spacing_double_semi))

            SearchBar(viewState.searchQuery, viewModel::onQueryChanged)

            Spacer(modifier = Modifier.height(Dimens.spacing_double_semi))

            SortOptions(
                options = viewState.sortOptions,
                onClick = viewModel::onSortOptionClick,
                sortDirection = viewState.sortDirection,
                onSortDirectionClick = viewModel::onSortDirectionClick
            )

            Spacer(modifier = Modifier.height(Dimens.spacing_triple))

            CountriesPager(viewState.countries, viewModel::onCountryClick)
        }
    }
}

@Composable
private fun Header() {
    Column(modifier = Modifier.padding(horizontal = Dimens.spacing_normal)) {
        Text(text = stringResource(id = R.string.greeting))

        Spacer(modifier = Modifier.height(Dimens.spacing_semi))

        Text(text = stringResource(id = R.string.header_title), style = Typography.a4TextStyle)
    }
}

@Composable
private fun SearchBar(
    searchValue: String, onSearchValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = Dimens.spacing_normal)
            .fillMaxWidth(),
        value = searchValue,
        onValueChange = onSearchValueChanged,
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = null)
        },
        label = {
            Text(text = stringResource(id = R.string.search))
        },
        shape = RoundedCornerShape(32.dp),
    )
}

@Composable
private fun SortOptions(
    options: List<SortOption>,
    onClick: (SortOption.Type) -> Unit,
    sortDirection: SortDirection,
    onSortDirectionClick: () -> Unit,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = Dimens.spacing_normal),
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing_semi)
    ) {
        item {
            SortDirectionOption(onSortDirectionClick, sortDirection)
        }

        items(options) {
            SortOption(it, onClick)
        }
    }
}

@Composable
private fun SortDirectionOption(onClick: () -> Unit, sortDirection: SortDirection) {
    OutlinedButton(
        onClick = onClick, colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color(0xFF29243A)
        ), border = BorderStroke(0.dp, Color.Transparent)
    ) {
        val icon = when (sortDirection) {
            SortDirection.ASCENDING -> Icons.Default.KeyboardArrowUp
            SortDirection.DESCENDING -> Icons.Default.KeyboardArrowDown
        }

        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp))
    }
}

@Composable
private fun SortOption(
    sortOption: SortOption, onClick: (SortOption.Type) -> Unit
) {
    OutlinedButton(
        onClick = { onClick(sortOption.type) }, colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (sortOption.selected) ColorScheme.primary else Color(0xFF29243A),
        ), border = BorderStroke(0.dp, Color.Transparent)
    ) {
        val color = if (sortOption.selected) Color.White else Color(0xFF716E7F)

        Text(text = stringResource(id = sortOption.nameResId), color = color)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountriesPager(countries: List<Country>, onClick: (Country) -> Unit) {
    val state = rememberPagerState { countries.size }

    val scale by remember {
        derivedStateOf {
            1f - (state.currentPageOffsetFraction.absoluteValue) * .3f
        }
    }

    Box(modifier = Modifier.padding(horizontal = Dimens.spacing_normal)) {

        HorizontalPager(
            state = state,
            modifier = Modifier
                .scale(1f, scaleY = scale)
                .aspectRatio(1f),
        ) { page ->
            Box(modifier = Modifier
                .clickable { onClick(countries[page]) }
                .aspectRatio(1f)
                .graphicsLayer {
                    val pageOffset = state.offsetForPage(page)
                    val offScreenRight = pageOffset < 0f
                    val deg = 105f
                    val interpolated = FastOutLinearInEasing.transform(pageOffset.absoluteValue)
                    rotationY = min(interpolated * if (offScreenRight) deg else -deg, 90f)

                    transformOrigin = TransformOrigin(
                        pivotFractionX = if (offScreenRight) 0f else 1f, pivotFractionY = .5f
                    )
                }
                .drawWithContent {
                    val pageOffset = state.offsetForPage(page)

                    this.drawContent()
                    drawRect(
                        Color.Black.copy((pageOffset.absoluteValue * .7f))
                    )
                }, contentAlignment = Alignment.Center
            ) {
                CountryPagerItem(countries[page])
            }
        }
    }
}


@Composable
private fun CountryPagerItem(country: Country) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF29243A), shape = RoundedCornerShape(52.dp))
            .padding(bottom = Dimens.spacing_double_semi),
    ) {
        DefaultStateImage(
            url = country.imageUrl,
            modifier = Modifier
                .padding(Dimens.spacing_normal)
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(32.dp))
        )

        Spacer(modifier = Modifier.height(Dimens.spacing_normal))

        Row(
            modifier = Modifier
                .padding(horizontal = Dimens.spacing_normal)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = country.commonName)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = country.populationCount.toString())

                Spacer(modifier = Modifier.width(Dimens.spacing_basic))

                Icon(
                    painter = painterResource(id = R.drawable.ic_people),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.spacing_normal))

        Row(
            modifier = Modifier
                .padding(horizontal = Dimens.spacing_normal)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = country.flagUrl,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(Dimens.spacing_basic))

                Text(text = country.capitalCityName.orEmpty())
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "${country.areaKm} km2")

                Spacer(modifier = Modifier.width(Dimens.spacing_basic))

                Icon(
                    painter = painterResource(id = R.drawable.ic_area),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}