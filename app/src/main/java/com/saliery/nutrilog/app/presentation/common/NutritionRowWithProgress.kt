package com.saliery.nutrilog.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.app.presentation.helper.trimSmart
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun NutritionRowWithProgress(
    label: String,
    value: Double?,
    total: Double?,
    color: Color,
    unit: String
) {
    val progress = remember(value, total) {
        if (value != null && total != null && total > 0) (value / total).toFloat() else 0f
    }

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = OnboardingGlassTokens.TextPrimary
            )
            Text(
                text = "${value?.trimSmart() ?: "0"} $unit",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = OnboardingGlassTokens.TextPrimary
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(OnboardingGlassTokens.GlassBorder)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(color)
            )


        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun NutritionRowWithProgressPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        NutritionRowWithProgress(
            label = "Protein",
            color = OnboardingGlassTokens.BmiRed,
            value = 34.2,
            total = 65.4,
            unit = "g"
        )
    }
}