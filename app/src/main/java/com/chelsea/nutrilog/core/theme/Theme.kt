package com.chelsea.nutrilog.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NutriLogLightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = White,
    primaryContainer = LightGreen,
    onPrimaryContainer = TertiaryGreen,
    secondary = SecondaryGreen,
    onSecondary = White,
    secondaryContainer = LightGreen,
    onSecondaryContainer = TertiaryGreen,
    tertiary = TertiaryGreen,
    onTertiary = White,
    tertiaryContainer = LightGreen,
    onTertiaryContainer = TertiaryGreen,
    error = ErrorRed,
    onError = White,
    errorContainer = Color(0xFFFFEBEE),
    onErrorContainer = ErrorRed,
    background = White,
    onBackground = DarkGray,
    surface = LightGray,
    onSurface = DarkGray,
    surfaceVariant = VeryLightGreen,
    onSurfaceVariant = MediumGray,
    outline = MediumGray
)

@Composable
fun NutriLogTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NutriLogLightColorScheme,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
