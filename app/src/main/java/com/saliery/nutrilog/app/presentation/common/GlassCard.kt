package com.saliery.nutrilog.app.presentation.common

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    hazeState: HazeState? = null,
    cornerRadius: Dp = 24.dp,
    glow: Boolean = false,
    enableHaze: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(20.dp),
    surfaceColor: Color = OnboardingGlassTokens.GlassSurfaceStrong,
    borderColor: Color = OnboardingGlassTokens.GlassBorder,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)

    val cardModifier = modifier
        .then(
            if (glow) {
                Modifier.shadow(
                    elevation = 18.dp,
                    shape = shape,
                    ambientColor = OnboardingGlassTokens.Glow,
                    spotColor = OnboardingGlassTokens.Glow
                )
            } else {
                Modifier
            }
        )
        .then(
            if (
                enableHaze &&
                hazeState != null &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
            ) {
                Modifier.hazeEffect(
                    state = hazeState,
                    style = HazeStyle(
                        blurRadius = 18.dp,
                        tint = HazeTint(surfaceColor)
                    ),
                    block = null
                )
            } else {
                Modifier
            }
        )
        .clip(shape)
        .background(surfaceColor)
        .border(
            width = 1.dp,
            color = borderColor,
            shape = shape
        )

    Box(
        modifier = cardModifier
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = if (isSystemInDarkTheme()) 0.18f else 0.10f),
                            Color.White.copy(alpha = if (isSystemInDarkTheme()) 0.05f else 0.04f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            content()
        }
    }
}

@Preview(
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE,
    showBackground = true
)
@Composable
private fun GlassCardPreview() {
    NutriLogTheme(
        darkTheme = true
    ) {
        GlassCard(
            glow = true,
            modifier = Modifier.height(200.dp)
        ) {
            Text(text = "Hello")
        }
    }
}