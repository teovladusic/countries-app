package com.puzzle_agency.countriesapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.puzzle_agency.countriesapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val InterFont = FontFamily(
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_semi_bold, FontWeight.SemiBold),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_regular, FontWeight.Normal),
)

val Typography.a4TextStyle: TextStyle
    get() = TextStyle(
        fontFamily = InterFont,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Bold
    )

val Typography.a3TextStyle: TextStyle
    get() = TextStyle(
        fontFamily = InterFont,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Bold
    )

val Typography.a2SemiBoldTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = InterFont,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.a2BoldTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = InterFont,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.a1RegularTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = InterFont,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Normal
    )

val Typography.a1BoldTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = InterFont,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Bold
    )

val Typography.b0BoldTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = InterFont,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Bold
    )

val Typography.b0SemiBoldTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = InterFont,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.b0MediumTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = InterFont,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Medium
    )

val Typography.b0RegularTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = InterFont,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Normal
    )

val Typography.b1MediumTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = InterFont,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Medium
    )

val Typography.b1SemiBoldTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = InterFont,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.b2TextStyle: TextStyle
    get() = TextStyle(
        fontFamily = InterFont,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Medium
    )

