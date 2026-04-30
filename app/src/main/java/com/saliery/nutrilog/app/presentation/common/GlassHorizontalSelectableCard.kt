package com.saliery.nutrilog.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun GlassHorizontalSelectableCard(
    isSelected: Boolean,
    leadingIcon: Painter? = null,
    title: String,
    subtitle: String? = null,
    supporting: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(22.dp)

    val containerColor = if (isSelected) {
        OnboardingGlassTokens.GlassSurfaceStrong
    } else {
        OnboardingGlassTokens.GlassSurface
    }

    val borderColor = if (isSelected) {
        OnboardingGlassTokens.PrimaryBright.copy(alpha = 0.75f)
    } else {
        OnboardingGlassTokens.GlassBorder
    }

    val iconTint = if (isSelected) {
        OnboardingGlassTokens.Primary
    } else {
        OnboardingGlassTokens.TextSecondary
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isSelected) 14.dp else 8.dp,
                shape = shape,
                ambientColor = if (isSelected) OnboardingGlassTokens.Glow else Color.Transparent,
                spotColor = if (isSelected) OnboardingGlassTokens.Glow else Color.Transparent
            )
            .clip(shape)
            .background(containerColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            )
            .clickable(onClick = onClick)
            .padding(18.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.End
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (leadingIcon != null) {
                    Box(
                        modifier = Modifier.size(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = leadingIcon,
                            contentDescription = null,
                            tint = iconTint,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        color = OnboardingGlassTokens.TextPrimary,
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (!subtitle.isNullOrBlank()) {
                        Text(
                            text = subtitle,
                            color = OnboardingGlassTokens.TextSecondary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            if (!supporting.isNullOrBlank()) {
                Text(
                    text = supporting,
                    color = OnboardingGlassTokens.TextTertiary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    showBackground = false,
    showSystemUi = false
)
@Composable
private fun GlassHorizontalSelectableCardPreview() {
    NutriLogTheme(
        darkTheme = true
    ) {

        GlassHorizontalSelectableCard(
            isSelected = false,
            title = "Low activity",
            subtitle = "If you don't do any exercises, even don't walk outside",
            supporting = "supporting info",
            leadingIcon = painterResource(R.drawable.computer_user_type_work_do_svgrepo_com),
            onClick = {}
        )
    }
}