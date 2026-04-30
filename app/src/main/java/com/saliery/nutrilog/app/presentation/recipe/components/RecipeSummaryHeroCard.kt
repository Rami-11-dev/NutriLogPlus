package com.saliery.nutrilog.app.presentation.recipe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.presentation.common.GlassCard
import com.saliery.nutrilog.app.presentation.common.NutritionSimpleRow
import com.saliery.nutrilog.app.presentation.helper.previewRecipeNutritionSummaryUiModel
import com.saliery.nutrilog.app.presentation.helper.trimSmart
import com.saliery.nutrilog.app.presentation.product.components.MacroElement
import com.saliery.nutrilog.app.presentation.recipe.RecipeNutritionSummaryUiModel
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun RecipeSummaryHeroCard(
    summary: RecipeNutritionSummaryUiModel,
    servingsYieldInput: String,
    hazeState: HazeState
) {
    val gramsUnit = stringResource(R.string.grams_unit_short)

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 230.dp),
        hazeState = hazeState,
        glow = true,
        contentPadding = PaddingValues(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.recipe_summary_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = OnboardingGlassTokens.TextPrimary
                )

                Text(
                    text = stringResource(R.string.recipe_yield_short, servingsYieldInput),
                    style = MaterialTheme.typography.bodySmall,
                    color = OnboardingGlassTokens.TextSecondary
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MacroElement(
                    label = stringResource(R.string.kcal_unit_str),
                    value = summary.totalCalories.trimSmart(),
                    accentColor = MaterialTheme.colorScheme.primary
                )
                MacroElement(
                    label = stringResource(R.string.protein_str),
                    value = "${summary.totalProteins.trimSmart()} $gramsUnit",
                    accentColor = OnboardingGlassTokens.ProteinColor
                )
                MacroElement(
                    label = stringResource(R.string.fat_str),
                    value = "${summary.totalFats.trimSmart()} $gramsUnit",
                    accentColor = OnboardingGlassTokens.FatsColor
                )
                MacroElement(
                    label = stringResource(R.string.carbs_str),
                    value = "${summary.totalCarbs.trimSmart()} $gramsUnit",
                    accentColor = OnboardingGlassTokens.CarbsColor
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                OnboardingGlassTokens.ProteinColor,
                                OnboardingGlassTokens.FatsColor,
                                OnboardingGlassTokens.CarbsColor
                            )
                        )
                    )
            )

            HorizontalDivider(
                color = OnboardingGlassTokens.GlassBorder
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.per_serving_str),
                    style = MaterialTheme.typography.labelLarge,
                    color = OnboardingGlassTokens.TextSecondary
                )

                NutritionSimpleRow(stringResource(R.string.calories_str), summary.perServingCalories, stringResource(R.string.kcal_unit_str))
                NutritionSimpleRow(stringResource(R.string.protein_str), summary.perServingProteins, gramsUnit)
                NutritionSimpleRow(stringResource(R.string.fat_str), summary.perServingFats, gramsUnit)
                NutritionSimpleRow(stringResource(R.string.carbs_str), summary.perServingCarbs, gramsUnit)
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun RecipeSummaryHeroCardPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        RecipeSummaryHeroCard(
            summary = previewRecipeNutritionSummaryUiModel(),
            servingsYieldInput = "1",
            hazeState = rememberHazeState()
        )
    }
}