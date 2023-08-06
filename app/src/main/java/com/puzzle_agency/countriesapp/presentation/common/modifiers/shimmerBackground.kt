package com.puzzle_agency.countriesapp.presentation.common.modifiers

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import com.puzzle_agency.countriesapp.ui.theme.Grey100

fun Modifier.shimmerBackground(shape: Shape = RectangleShape): Modifier = composed {

    val transition = rememberInfiniteTransition()
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 500f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 2000, easing = LinearEasing),
            RepeatMode.Restart
        ),
    )
    val shimmerColors = listOf(Grey100.copy(alpha = 0.9f), Grey100.copy(alpha = 0.2f))

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation - (translateAnimation / 0.2f), 0f),
        end = Offset(translateAnimation, 0f),
        tileMode = TileMode.Mirror
    )

    return@composed this.then(background(brush, shape))
}