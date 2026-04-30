package com.saliery.nutrilog.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

object OnboardingGlassTokens {

    val ScreenBackground: Color
        @Composable get() = if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.background
        } else {
            lerp(
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
                0.10f
            )
        }

    val Primary: Color
        @Composable get() = MaterialTheme.colorScheme.primary
    val PrimaryBright = Color(0xFF58C589)
    val PrimarySoft = Color(0x332CA56B)

    val TextPrimary: Color
        @Composable get() = MaterialTheme.colorScheme.onSurface

    val TextSecondary: Color
        @Composable get() = if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.onSurfaceVariant
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.88f)
        }

    val TextTertiary: Color
        @Composable get() = if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.72f)
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.68f)
        }

    val SelectedIcon: Color
        @Composable get() = MaterialTheme.colorScheme.primary
    val SelectedBorder: Color = PrimaryBright.copy(alpha = 0.75f)

    val GlassSurface: Color
        @Composable get() = if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.surface.copy(alpha = 0.30f)
        } else {
            lerp(
                MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                0.18f
            ).copy(alpha = 0.94f)
        }

    val GlassSurfaceStrong: Color
        @Composable get() = if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.surface.copy(alpha = 0.42f)
        } else {
            lerp(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
                0.12f
            ).copy(alpha = 0.98f)
        }

    val GlassBorder: Color
        @Composable get() = if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)
        } else {
            MaterialTheme.colorScheme.outline.copy(alpha = 0.16f)
        }

    val Glow: Color
        @Composable get() = if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.24f)
        } else {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.06f)
        }

    val BmiBlue = Color(0xFF58A9FF)
    val BmiGreen = Color(0xFF2CA56B)
    val BmiYellow = Color(0xFFF0B34F)
    val BmiRed = Color(0xFFEF7070)

    val ProteinColor = Color(0xFF58C589)
    val FatsColor = Color(0xFFF0B34F)
    val CarbsColor = Color(0xFF58A9FF)
}