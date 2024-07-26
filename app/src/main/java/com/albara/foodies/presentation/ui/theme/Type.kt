package com.albara.foodies.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.albara.foodies.R


val roboto = FontFamily(
    listOf(
        Font(R.font.roboto_bold, FontWeight.Bold),
        Font(R.font.roboto_black, FontWeight.Black),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_regular, FontWeight.Normal),

    )
)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    headlineLarge = TextStyle (
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        lineHeight = 36.sp
    )
)