package com.neurodose.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Light Color Scheme for NeuroDose
 *
 * Material Design 3 light theme with NeuroDose-specific colors.
 * Used on devices with light system theme preference.
 */
private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,

    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,

    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight,
    tertiaryContainer = TertiaryContainerLight,
    onTertiaryContainer = OnTertiaryContainerLight,

    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,

    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,

    outline = OutlineLight,
    outlineVariant = OutlineVariantLight
)

/**
 * Dark Color Scheme for NeuroDose
 *
 * Material Design 3 dark theme with NeuroDose-specific colors.
 * Used on devices with dark system theme preference.
 */
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,

    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,

    tertiary = TertiaryDark,
    onTertiary = OnTertiaryDark,
    tertiaryContainer = TertiaryContainerDark,
    onTertiaryContainer = OnTertiaryContainerDark,

    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,

    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,

    outline = OutlineDark,
    outlineVariant = OutlineVariantDark
)

/**
 * NeuroDoseTheme is the main theme composable for the app.
 *
 * This theme applies Material Design 3 styling with NeuroDose-specific colors.
 * It supports:
 * - Light and dark themes (based on system preference)
 * - Dynamic colors on Android 12+ (Material You)
 * - Custom typography
 *
 * @param darkTheme Whether to use dark theme (defaults to system preference)
 * @param dynamicColor Whether to use dynamic colors (Android 12+)
 * @param content The content to apply the theme to
 */
@Composable
fun NeuroDoseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // Select color scheme based on theme preference and device capabilities
    val colorScheme = when {
        // Use dynamic colors on Android 12+ if enabled
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }
        // Use NeuroDose dark theme
        darkTheme -> DarkColorScheme
        // Use NeuroDose light theme
        else -> LightColorScheme
    }

    // Apply Material Design 3 theme with selected colors and typography
    MaterialTheme(
        colorScheme = colorScheme,
        typography = NeuroDoseTypography,
        content = content
    )
}