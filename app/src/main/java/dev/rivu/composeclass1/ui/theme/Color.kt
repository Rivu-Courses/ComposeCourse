package dev.rivu.composeclass1.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material.MaterialTheme as Material2Theme

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

@Composable
fun getMaterialColors(): Colors {
    return Material2Theme.colors.copy(
        primary = Purple80,
        primaryVariant = PurpleGrey80,
        secondary = Pink80
    )
}