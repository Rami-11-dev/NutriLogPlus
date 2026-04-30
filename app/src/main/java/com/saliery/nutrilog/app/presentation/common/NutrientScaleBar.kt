package com.saliery.nutrilog.app.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import java.util.Locale

@Composable
fun NutrientScaleBar(
    title: String,
    value: Float,
    unit: String,
    minValue: Float = 0f,
    maxValue: Float,
    modifier: Modifier = Modifier,
    scaleMultiplier: Float = 1.25f,
    barHeight: Dp = 6.dp,
    gap: Dp = 2.dp,
) {
    val neutralColor = Color(0xFF3A3A3A)
    val underColor = Color(0xFF9BE7A6)
    val healthyColor = Color(0xFF3CCB6A)
    val excessColor = Color(0xFFFFA24A)
    val healthyTrackColor = Color(0x223CCB6A)
    val markerColor = Color(0xFF7A7A7A)

    val scaleMax = (maxValue * scaleMultiplier).coerceAtLeast(maxValue + 0.0001f)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = OnboardingGlassTokens.TextSecondary,
            )

            Text(
                text = "${value.trimDecimal()} $unit",
                style = MaterialTheme.typography.titleMedium,
                color = OnboardingGlassTokens.TextSecondary
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        NutrientProgressBar(
            value = value,
            minValue = minValue,
            maxValue = maxValue,
            scaleMax = scaleMax,
            barHeight = barHeight,
            gap = gap,
            neutralColor = neutralColor,
            underColor = underColor,
            healthyColor = healthyColor,
            excessColor = excessColor,
            healthyTrackColor = healthyTrackColor,
            markerColor = markerColor,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun Float.trimDecimal(): String {
    return if (this % 1f == 0f) {
        this.toInt().toString()
    } else {
        String.format(Locale.US, "%.1f", this)
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun NutrientScaleBarPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        NutrientScaleBar(
            title = "Fiber",
            value = 4.1f,
            unit = "g",
            minValue = 13f,
            maxValue = 20f,
            gap = 6.dp
        )
    }
}