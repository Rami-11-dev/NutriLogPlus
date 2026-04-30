package com.saliery.nutrilog.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.business.DonutChartSegment
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun MacroTargetsChart(
    proteinGrams: Float,
    fatGrams: Float,
    carbsGrams: Float,
    modifier: Modifier = Modifier
) {
    val segments = listOf(
        DonutChartSegment("Protein", proteinGrams, OnboardingGlassTokens.ProteinColor),
        DonutChartSegment("Fat", fatGrams, OnboardingGlassTokens.FatsColor),
        DonutChartSegment("Carbs", carbsGrams, OnboardingGlassTokens.CarbsColor)
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GapDonutChart(
            segments = segments,
            centerContent = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.macros_str),
                        style = MaterialTheme.typography.labelLarge,
                        color = OnboardingGlassTokens.TextSecondary
                    )
                    Text(
                        text = "P / F / C",
                        style = MaterialTheme.typography.headlineSmall,
                        color = OnboardingGlassTokens.TextPrimary
                    )
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MacroLegendItem(stringResource(R.string.protein_str), proteinGrams, OnboardingGlassTokens.ProteinColor)
            MacroLegendItem(stringResource(R.string.fat_str), fatGrams, OnboardingGlassTokens.FatsColor)
            MacroLegendItem(stringResource(R.string.carbs_str), carbsGrams, OnboardingGlassTokens.CarbsColor)
        }
    }
}

@Composable
private fun MacroLegendItem(
    label: String,
    grams: Float,
    color: Color
) {

    Box(
        modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(color)
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = OnboardingGlassTokens.TextSecondary
        )

        Text(
            text = stringResource(R.string.grams_short_str, grams.toInt()),
            style = MaterialTheme.typography.headlineMedium,
            color = OnboardingGlassTokens.TextPrimary
        )
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun MacroTargetChartPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        MacroTargetsChart(
            proteinGrams = 160f,
            fatGrams = 70f,
            carbsGrams = 240f
        )
    }
}