package my.tipcalculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

var primarySurface: Color = Color.White
var secondarySurface: Color = Color.White

private val LightColorPalette = lightColors(
    primary = sofia_theme_light_primary,
    primaryVariant = sofia_theme_light_primaryVariant,
    secondary = sofia_theme_light_secondary,
    secondaryVariant = sofia_theme_light_secondaryVariant,
    background = sofia_theme_light_background,
    surface = sofia_theme_light_surface,
    error = sofia_theme_light_error,
    onPrimary = sofia_theme_light_onPrimary,
    onSecondary = sofia_theme_light_onSecondary,
    onBackground = sofia_theme_light_onBackground,
    onSurface = sofia_theme_light_onSurface,
    onError = sofia_theme_light_onError
)

private val DarkColorPalette = darkColors(
    primary = sofia_theme_dark_primary,
    primaryVariant = sofia_theme_dark_primaryVariant,
    secondary = sofia_theme_dark_secondary,
    secondaryVariant = sofia_theme_dark_secondaryVariant,
    background = sofia_theme_dark_background,
    surface = sofia_theme_dark_surface,
    error = sofia_theme_dark_error,
    onPrimary = sofia_theme_dark_onPrimary,
    onSecondary = sofia_theme_dark_onSecondary,
    onBackground = sofia_theme_dark_onBackground,
    onSurface = sofia_theme_dark_onSurface,
    onError = sofia_theme_dark_onError
)

@Composable
fun TipCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    primarySurface = if(darkTheme) {
        sofia_theme_dark_primaryContainer
    } else {
        sofia_theme_light_primaryContainer
    }
    secondarySurface = if(darkTheme) {
        sofia_theme_dark_secondaryContainer
    } else {
        sofia_theme_light_secondaryContainer
    }

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

    systemUiController.setSystemBarsColor(
        color = colors.background.copy(alpha = 0.95F),
        darkIcons = !darkTheme
    )

}