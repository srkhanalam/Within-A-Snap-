package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SnapGold,
    onPrimary = Color.Black,
    primaryContainer = SnapGoldContainer,
    onPrimaryContainer = SnapOnGoldContainer,
    secondary = SnapViolet,
    onSecondary = Color.White,
    secondaryContainer = SnapVioletContainer,
    onSecondaryContainer = Color(0xFFE9D5FF),
    tertiary = SnapEmerald,
    onTertiary = Color.Black,
    tertiaryContainer = SnapEmeraldContainer,
    onTertiaryContainer = Color(0xFFA7F3D0),
    background = SnapBackgroundDark,
    onBackground = SnapTextPrimaryDark,
    surface = SnapSurfaceDark,
    onSurface = SnapTextPrimaryDark,
    surfaceVariant = SnapSurfaceVariantDark,
    onSurfaceVariant = SnapTextSecondaryDark,
    outline = SnapBorderDark,
    outlineVariant = Color(0xFF1E2638),
    error = SnapRose,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = SnapGoldDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFEF3C7),
    onPrimaryContainer = Color(0xFF78350F),
    secondary = SnapViolet,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEDE9FE),
    onSecondaryContainer = Color(0xFF4C1D95),
    tertiary = SnapEmerald,
    onTertiary = Color.White,
    background = SnapBackgroundLight,
    onBackground = SnapTextPrimaryLight,
    surface = SnapSurfaceLight,
    onSurface = SnapTextPrimaryLight,
    surfaceVariant = SnapSurfaceVariantLight,
    onSurfaceVariant = SnapTextSecondaryLight,
    outline = SnapBorderLight,
    outlineVariant = Color(0xFFCBD5E1),
    error = SnapRose,
    onError = Color.White
)

@Composable
fun WithinASnapTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
