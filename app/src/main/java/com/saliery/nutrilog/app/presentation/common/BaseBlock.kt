package com.saliery.nutrilog.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
fun BaseBlock(
    title: String,
    subtitle: String?,
    supporting: String?,
    leadingIcon: Painter?,
    isIconColorful: Boolean = false,
    iconTint: Color = OnboardingGlassTokens.TextSecondary,
    iconAmbient: Color = Color.Transparent,
    iconSpot: Color = Color.Transparent,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(22.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = shape,
                ambientColor = Color.Transparent,
                spotColor = Color.Transparent
            )
            .clip(shape)
            .background(OnboardingGlassTokens.GlassSurface)
            .border(
                width = 1.dp,
                color = OnboardingGlassTokens.GlassBorder,
                shape = shape
            )
            .padding(18.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (leadingIcon != null) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp),
                                ambientColor = iconAmbient,
                                spotColor = iconSpot
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = leadingIcon,
                            contentDescription = null,
                            tint = if (isIconColorful) Color.Unspecified else iconTint,
                            modifier = Modifier.size(40.dp)
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
                        style = MaterialTheme.typography.titleLarge
                    )

                    if (!subtitle.isNullOrBlank()) {
                        Text(
                            text = subtitle,
                            color = OnboardingGlassTokens.TextSecondary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                content = content
            )

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
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun BaseBlockPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        BaseBlock(
            title = "title",
            subtitle = "subtitle",
            supporting = "supporting",
            leadingIcon = painterResource(R.drawable.weight_svgrepo_com)
        ) {
            BmiScale(bmi = 22.3f)
        }
    }
}