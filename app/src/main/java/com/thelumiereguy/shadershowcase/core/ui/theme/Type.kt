package com.thelumiereguy.shadershowcase.core.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.thelumiereguy.shadershowcase.R

private val BlackMango = FontFamily(
    listOf(
        Font(R.font.black_mango_regular, FontWeight.Normal),
        Font(R.font.black_mango_medium, FontWeight.Medium),
        Font(R.font.black_mango_bold, FontWeight.Bold),
        Font(R.font.black_mango_black, FontWeight.Black)
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    h5 = TextStyle(
        fontFamily = BlackMango,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontFamily = BlackMango,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)