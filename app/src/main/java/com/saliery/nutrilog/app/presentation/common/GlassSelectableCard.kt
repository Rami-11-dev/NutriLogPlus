package com.saliery.nutrilog.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun GlassSelectableCard(
    selected: Boolean,
    title: String,
    subtitle: String? = null,
    icon: Painter? = null,
    iconTint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(22.dp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(
                elevation = if (selected) 14.dp else 8.dp,
                shape = shape,
                ambientColor = if (selected) OnboardingGlassTokens.Glow else Color.Transparent,
                spotColor = if (selected) OnboardingGlassTokens.Glow else Color.Transparent
            )
            .clip(shape)
            .background(
                if (selected) OnboardingGlassTokens.GlassSurfaceStrong
                else OnboardingGlassTokens.GlassSurface
            )
            .border(
                width = 1.dp,
                color = if (selected) {
                    OnboardingGlassTokens.PrimaryBright.copy(alpha = 0.6f)
                } else {
                    OnboardingGlassTokens.GlassBorder
                },
                shape = shape
            )
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (icon != null) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.White.copy(alpha = 0.16f))
                        .border(
                            1.dp,
                            OnboardingGlassTokens.GlassBorder,
                            RoundedCornerShape(18.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = OnboardingGlassTokens.TextPrimary
                )
                if (subtitle != null) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnboardingGlassTokens.TextSecondary
                    )
                }
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE,
    showBackground = true
)
@Composable
private fun GlassSelectableCardPreview() {
    NutriLogTheme(
        darkTheme = true
    ) {
        GlassSelectableCard(
            selected = false,
            title = "Female",
            subtitle = null,
            icon = null,
            modifier = Modifier,
            iconTint = Color.Red,
            onClick = {}
        )
    }
}