package com.neurodose.app.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * NeuroDose Color Palette - Material Design 3
 *
 * Colors are carefully chosen to represent cognitive enhancement and science:
 * - Primary: Deep Purple (brain/intelligence)
 * - Secondary: Teal (health/wellness)
 * - Tertiary: Amber (energy/focus)
 * - Error: Red (warnings/alerts)
 */

// Light Theme Colors
val PrimaryLight = Color(0xFF6750A4)      // Deep Purple - Primary action
val OnPrimaryLight = Color(0xFFFFFFFF)    // White text on primary
val PrimaryContainerLight = Color(0xFFEADDFF)  // Light purple background
val OnPrimaryContainerLight = Color(0xFF21005E)  // Dark purple text

val SecondaryLight = Color(0xFF625B71)    // Muted Purple - Secondary action
val OnSecondaryLight = Color(0xFFFFFFFF)  // White text on secondary
val SecondaryContainerLight = Color(0xFFE8DEF8)  // Light purple background
val OnSecondaryContainerLight = Color(0xFF1E192B)  // Dark text

val TertiaryLight = Color(0xFF7D5260)     // Mauve - Tertiary accent
val OnTertiaryLight = Color(0xFFFFFFFF)   // White text on tertiary
val TertiaryContainerLight = Color(0xFFFFD8E4)   // Light mauve background
val OnTertiaryContainerLight = Color(0xFF31111D)  // Dark text

val ErrorLight = Color(0xFFB3261E)        // Red - Errors and warnings
val OnErrorLight = Color(0xFFFFFFFF)      // White text on error
val ErrorContainerLight = Color(0xFFF9DEDC)  // Light red background
val OnErrorContainerLight = Color(0xFF410E0B)  // Dark red text

val BackgroundLight = Color(0xFFFFFBFE)   // Off-white background
val OnBackgroundLight = Color(0xFF1C1B1F)  // Dark text on background
val SurfaceLight = Color(0xFFFFFBFE)      // Surface color
val OnSurfaceLight = Color(0xFF1C1B1F)    // Text on surface
val SurfaceVariantLight = Color(0xFFE7E0EC)  // Variant surface
val OnSurfaceVariantLight = Color(0xFF49454E)  // Text on variant

val OutlineLight = Color(0xFF79747E)      // Outline/borders
val OutlineVariantLight = Color(0xFFCAC7D0)  // Variant outline

// Dark Theme Colors
val PrimaryDark = Color(0xFFD0BCFF)       // Light Purple - Primary action
val OnPrimaryDark = Color(0xFF371E55)     // Dark purple text on primary
val PrimaryContainerDark = Color(0xFF4F378B)  // Dark purple background
val OnPrimaryContainerDark = Color(0xFFEADDFF)  // Light text

val SecondaryDark = Color(0xFFCCC2DC)     // Light Muted Purple
val OnSecondaryDark = Color(0xFF332D41)   // Dark text on secondary
val SecondaryContainerDark = Color(0xFF4A4458)  // Dark background
val OnSecondaryContainerDark = Color(0xFFE8DEF8)  // Light text

val TertiaryDark = Color(0xFFEFB8C8)      // Light Mauve
val OnTertiaryDark = Color(0xFF492532)    // Dark text on tertiary
val TertiaryContainerDark = Color(0xFF633B48)  // Dark background
val OnTertiaryContainerDark = Color(0xFFFFD8E4)  // Light text

val ErrorDark = Color(0xFFF2B8B5)         // Light Red
val OnErrorDark = Color(0xFF601410)       // Dark text on error
val ErrorContainerDark = Color(0xFF8C1D18)  // Dark red background
val OnErrorContainerDark = Color(0xFFF9DEDC)  // Light text

val BackgroundDark = Color(0xFF1C1B1F)    // Dark background
val OnBackgroundDark = Color(0xFFE6E1E6)  // Light text on background
val SurfaceDark = Color(0xFF1C1B1F)       // Dark surface
val OnSurfaceDark = Color(0xFFE6E1E6)     // Light text on surface
val SurfaceVariantDark = Color(0xFF49454E)  // Dark variant surface
val OnSurfaceVariantDark = Color(0xFFCAC7D0)  // Light text on variant

val OutlineDark = Color(0xFF938F99)       // Light outline/borders
val OutlineVariantDark = Color(0xFF49454E)  // Variant outline

// Semantic Colors for Pharmacokinetic Visualization
val ConcentrationHigh = Color(0xFFD32F2F)  // Red - High concentration (warning)
val ConcentrationOptimal = Color(0xFF388E3C)  // Green - Optimal concentration
val ConcentrationLow = Color(0xFFFFA726)   // Orange - Low concentration (caution)
val ConcentrationCurve = Color(0xFF6750A4)  // Purple - Concentration curve line