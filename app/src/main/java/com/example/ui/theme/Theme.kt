package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class AppThemePreset(
    val id: String,
    val displayName: String,
    val subtitle: String,
    val primaryColor: Color,
    val accentColor: Color,
    val canvasColor: Color,
    val isDarkPreset: Boolean
) {
    GOLD_OBSIDIAN(
        id = "gold_obsidian",
        displayName = "Gold Obsidian",
        subtitle = "Luxury Champagne & Deep Onyx",
        primaryColor = Color(0xFFE5A93C),
        accentColor = Color(0xFF8B5CF6),
        canvasColor = Color(0xFF0B0E14),
        isDarkPreset = true
    ),
    EMERALD_HORIZON(
        id = "emerald_horizon",
        displayName = "Emerald Horizon",
        subtitle = "High-Growth Mint & Forest Slate",
        primaryColor = Color(0xFF10B981),
        accentColor = Color(0xFF14B8A6),
        canvasColor = Color(0xFF051311),
        isDarkPreset = true
    ),
    MIDNIGHT_SAPPHIRE(
        id = "midnight_sapphire",
        displayName = "Midnight Sapphire",
        subtitle = "Cyber Electric Blue & Deep Space",
        primaryColor = Color(0xFF38BDF8),
        accentColor = Color(0xFF818CF8),
        canvasColor = Color(0xFF060B18),
        isDarkPreset = true
    ),
    ROSE_BOUTIQUE(
        id = "rose_boutique",
        displayName = "Rose Boutique",
        subtitle = "Velvet Plum & Rose Gold Luxe",
        primaryColor = Color(0xFFFB7185),
        accentColor = Color(0xFFC084FC),
        canvasColor = Color(0xFF130910),
        isDarkPreset = true
    ),
    PLATINUM_LIGHT(
        id = "platinum_light",
        displayName = "Platinum Light",
        subtitle = "Clean Modern Minimalist & Crisp White",
        primaryColor = Color(0xFF2563EB),
        accentColor = Color(0xFFE5A93C),
        canvasColor = Color(0xFFF8FAFC),
        isDarkPreset = false
    ),
    SYSTEM_DEFAULT(
        id = "system_default",
        displayName = "System Adaptive",
        subtitle = "Matches Device System Settings",
        primaryColor = Color(0xFFE5A93C),
        accentColor = Color(0xFF10B981),
        canvasColor = Color(0xFF131822),
        isDarkPreset = true
    )
}

// 1. Gold Obsidian Scheme
private val GoldObsidianColorScheme = darkColorScheme(
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

// 2. Emerald Horizon Scheme
private val EmeraldHorizonColorScheme = darkColorScheme(
    primary = EmeraldPrimary,
    onPrimary = Color.Black,
    primaryContainer = EmeraldContainer,
    onPrimaryContainer = EmeraldOnContainer,
    secondary = EmeraldSecondary,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF042F2E),
    onSecondaryContainer = Color(0xFF99F6E4),
    tertiary = SnapGold,
    onTertiary = Color.Black,
    tertiaryContainer = SnapGoldContainer,
    onTertiaryContainer = SnapOnGoldContainer,
    background = EmeraldBackground,
    onBackground = Color(0xFFF0FDF4),
    surface = EmeraldSurface,
    onSurface = Color(0xFFF0FDF4),
    surfaceVariant = EmeraldSurfaceVariant,
    onSurfaceVariant = Color(0xFF94A3B8),
    outline = EmeraldBorder,
    outlineVariant = Color(0xFF134E48),
    error = SnapRose,
    onError = Color.White
)

// 3. Midnight Sapphire Scheme
private val MidnightSapphireColorScheme = darkColorScheme(
    primary = SapphirePrimary,
    onPrimary = Color.Black,
    primaryContainer = SapphireContainer,
    onPrimaryContainer = SapphireOnContainer,
    secondary = SapphireSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF1E1B4B),
    onSecondaryContainer = Color(0xFFC7D2FE),
    tertiary = SnapCyan,
    onTertiary = Color.Black,
    tertiaryContainer = SnapCyanContainer,
    onTertiaryContainer = Color(0xFFCFFAFE),
    background = SapphireBackground,
    onBackground = Color(0xFFF0F9FF),
    surface = SapphireSurface,
    onSurface = Color(0xFFF0F9FF),
    surfaceVariant = SapphireSurfaceVariant,
    onSurfaceVariant = Color(0xFF94A3B8),
    outline = SapphireBorder,
    outlineVariant = Color(0xFF1E3A8A),
    error = SnapRose,
    onError = Color.White
)

// 4. Rose Boutique Scheme
private val RoseBoutiqueColorScheme = darkColorScheme(
    primary = RosePrimary,
    onPrimary = Color.Black,
    primaryContainer = RoseContainer,
    onPrimaryContainer = RoseOnContainer,
    secondary = RoseSecondary,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF3B0764),
    onSecondaryContainer = Color(0xFFF3E8FF),
    tertiary = SnapGold,
    onTertiary = Color.Black,
    tertiaryContainer = SnapGoldContainer,
    onTertiaryContainer = SnapOnGoldContainer,
    background = RoseBackground,
    onBackground = Color(0xFFFFF1F2),
    surface = RoseSurface,
    onSurface = Color(0xFFFFF1F2),
    surfaceVariant = RoseSurfaceVariant,
    onSurfaceVariant = Color(0xFFA8A29E),
    outline = RoseBorder,
    outlineVariant = Color(0xFF4C0519),
    error = SnapRose,
    onError = Color.White
)

// 5. Platinum Light Scheme
private val PlatinumLightColorScheme = lightColorScheme(
    primary = Color(0xFF2563EB),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDBEAFE),
    onPrimaryContainer = Color(0xFF1E40AF),
    secondary = SnapViolet,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEDE9FE),
    onSecondaryContainer = Color(0xFF4C1D95),
    tertiary = SnapEmerald,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFD1FAE5),
    onTertiaryContainer = Color(0xFF065F46),
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
    preset: AppThemePreset = AppThemePreset.GOLD_OBSIDIAN,
    systemDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = when (preset) {
        AppThemePreset.GOLD_OBSIDIAN -> GoldObsidianColorScheme
        AppThemePreset.EMERALD_HORIZON -> EmeraldHorizonColorScheme
        AppThemePreset.MIDNIGHT_SAPPHIRE -> MidnightSapphireColorScheme
        AppThemePreset.ROSE_BOUTIQUE -> RoseBoutiqueColorScheme
        AppThemePreset.PLATINUM_LIGHT -> PlatinumLightColorScheme
        AppThemePreset.SYSTEM_DEFAULT -> if (systemDarkTheme) GoldObsidianColorScheme else PlatinumLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

