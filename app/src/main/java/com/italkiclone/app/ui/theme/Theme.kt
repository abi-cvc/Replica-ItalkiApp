package com.italkiclone.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ItalkiLightColors = lightColorScheme(
    primary = ItalkiCoral,
    onPrimary = SurfaceLight,
    primaryContainer = ItalkiCoralDark,
    secondary = ItalkiSlate,
    onSecondary = SurfaceLight,
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceLight,
    onSurface = TextPrimary,
    surfaceVariant = BackgroundLight,
    onSurfaceVariant = TextSecondary,
    outline = DividerLight,
    tertiary = ItalkiGreenAccent,
    error = ItalkiCoralDark,
)

@Composable
fun ItalkiCloneTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ItalkiLightColors,
        typography = ItalkiTypography,
        content = content,
    )
}
