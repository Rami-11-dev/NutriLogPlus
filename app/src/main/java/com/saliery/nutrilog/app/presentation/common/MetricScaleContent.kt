package com.saliery.nutrilog.app.presentation.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.app.domain.model.business.BmiScaleDefaults
import com.saliery.nutrilog.app.domain.model.business.ScaleBand
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun MetricScaleContent(
    title: String,
    valueText: String,
    badgeText: String,
    value: Float,
    minValue: Float,
    maxValue: Float,
    bands: List<ScaleBand>,
    ticks: List<String>,
    modifier: Modifier = Modifier,
    badgeColorOverride: Color? = null
) {
    require(maxValue > minValue) { "maxValue must be greater than minValue" }

    val target = value.coerceIn(minValue, maxValue)

    val animatedValue by animateFloatAsState(
        targetValue = target,
        animationSpec = tween(durationMillis = 800),
        label = "metric-scale-value"
    )

    val currentBand = remember(animatedValue, bands) {
        bands.firstOrNull { animatedValue >= it.start && animatedValue < it.end }
            ?: bands.last()
    }

    val percent = ((animatedValue - minValue) / (maxValue - minValue))
        .coerceIn(0f, 1f)

    val barShape = RoundedCornerShape(999.dp)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    color = OnboardingGlassTokens.TextSecondary,
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = valueText,
                    color = OnboardingGlassTokens.TextPrimary,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Surface(
                shape = RoundedCornerShape(999.dp),
                color = (badgeColorOverride ?: currentBand.color).copy(alpha = 0.16f),
                contentColor = badgeColorOverride ?: currentBand.color
            ) {
                Text(
                    text = badgeText.uppercase(),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val barY = size.height * 0.35f
                val barHeight = size.height * 0.18f
                val radius = barHeight / 2f
                val barWidth = size.width

                drawRoundRect(
                    brush = Brush.horizontalGradient(
                        colors = bands.map { it.color.copy(alpha = 0.95f) }
                    ),
                    topLeft = Offset(0f, barY),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(radius, radius)
                )

                val bandOverlayAlpha = 0.10f
                bands.forEach { band ->
                    val left = ((band.start - minValue) / (maxValue - minValue)) * barWidth
                    val right = ((band.end - minValue) / (maxValue - minValue)) * barWidth

                    if (currentBand == band) {
                        drawRoundRect(
                            color = Color.White.copy(alpha = bandOverlayAlpha),
                            topLeft = Offset(left, barY),
                            size = Size(right - left, barHeight),
                            cornerRadius = CornerRadius(radius, radius)
                        )
                    }

                    if (band != bands.last()) {
                        drawLine(
                            color = Color.White.copy(alpha = 0.24f),
                            start = Offset(right, barY),
                            end = Offset(right, barY + barHeight),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                }

                drawRoundRect(
                    color = Color.White.copy(alpha = 0.14f),
                    topLeft = Offset(0f, barY),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(radius, radius),
                    style = Stroke(width = 1.dp.toPx())
                )

                val x = percent * barWidth
                val centerY = barY + barHeight / 2f

                drawLine(
                    color = Color.White.copy(alpha = 0.88f),
                    start = Offset(x, barY - 14.dp.toPx()),
                    end = Offset(x, barY + barHeight + 14.dp.toPx()),
                    strokeWidth = 2.5.dp.toPx()
                )

                drawCircle(
                    color = Color.White,
                    radius = 9.dp.toPx(),
                    center = Offset(x, centerY)
                )

                drawCircle(
                    color = currentBand.color,
                    radius = 4.5.dp.toPx(),
                    center = Offset(x, centerY)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ticks.forEach { tick ->
                    Text(
                        text = tick,
                        color = OnboardingGlassTokens.TextTertiary,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun MetricScaleContentPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        
        MetricScaleContent(
            title = "title",
            valueText = "test",
            badgeText = "badge",
            value = 22.5f,
            minValue = 1.0f,
            maxValue = 50f,
            bands = BmiScaleDefaults.bands,
            ticks = BmiScaleDefaults.ticks,
            modifier = Modifier,
            badgeColorOverride = null
        )
    }
}