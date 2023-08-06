package com.puzzle_agency.countriesapp.presentation.common.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.puzzle_agency.countriesapp.R
import com.puzzle_agency.countriesapp.presentation.common.modifiers.shimmerBackground

@Composable
fun DefaultStateImage(
    url: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    shape: Shape = RectangleShape,
    contentScale: ContentScale = ContentScale.Crop
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .networkCachePolicy(CachePolicy.ENABLED)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .defaultMinSize(minHeight = 200.dp)
                        .background(Color(0xFF383445))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_photo),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shimmerBackground(shape = shape)
                )
            }

            else -> SubcomposeAsyncImageContent()
        }
    }
}