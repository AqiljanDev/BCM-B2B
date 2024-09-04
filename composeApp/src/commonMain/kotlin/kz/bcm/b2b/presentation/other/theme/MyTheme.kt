package kz.bcm.b2b.presentation.other.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = Color.White,
    secondary = Color.Red,
    secondaryVariant = Color.Yellow,

    background = Color(0xFFFFFFFF), // White
    surface = Color(0xFFFFFFFF), // White
    error = Color(0xFFB00020), // Red 800

    onPrimary = Color.Black,
    onSecondary = Color.Red, // Black
    onBackground = Color.Cyan, // Black
    onSurface = Color.Black, // Black
    onError = Color.White // White
)

private val DarkColorPalette = darkColors(
    primary = Color(0xFFBB86FC), // Purple 200
    primaryVariant = Color(0xFF6200EE), // Purple 500
    secondary = Color(0xFF03DAC6), // Teal 200
    secondaryVariant = Color(0xFF018786), // Teal 700

    background = Color(0xFF121212), // Dark Gray
    surface = Color(0xFF121212), // Dark Gray
    error = Color(0xFFCF6679), // Red 200

    onPrimary = Color.Black, // Black
    onSecondary = Color.Black, // Black
    onBackground = Color.White, // White
    onSurface = Color.White, // White
    onError = Color.Black // Black
)

@Composable
fun MyTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography, // Вы также можете настроить Typography
        shapes = Shapes, // Вы можете настроить формы
        content = content
    )
}