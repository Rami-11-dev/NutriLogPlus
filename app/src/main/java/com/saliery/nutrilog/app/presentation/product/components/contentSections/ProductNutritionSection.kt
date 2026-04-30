package com.saliery.nutrilog.app.presentation.product.components.contentSections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.product.NutritionFacts
import com.saliery.nutrilog.app.presentation.common.NutritionSimpleRow
import com.saliery.nutrilog.app.presentation.helper.previewFoodProduct
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import java.util.Locale.getDefault

@Composable
fun ProductNutritionSection(
    facts: NutritionFacts,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val gramsUnit = stringResource(R.string.grams_unit_short)

        Text(
            text = stringResource(R.string.per_hundred_grams_of_product_str),
            style = MaterialTheme.typography.labelMedium,
            color = OnboardingGlassTokens.TextTertiary
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 4.dp),
            color = OnboardingGlassTokens.GlassBorder
        )

        // Energy
        NutritionGroup(title = stringResource(R.string.energy_value_str)) {
            if (facts.kcal != null) {
                NutritionSimpleRow(label = stringResource(R.string.energy_str), value = facts.kcal, unit = stringResource(
                    R.string.kcal_unit_str
                ).lowercase(getDefault())
                )
            }
            if (facts.kJ != null) {
                NutritionSimpleRow(label = stringResource(R.string.energy_str), value = facts.kJ, unit = stringResource(R.string.kJ_unit_str))
            }
        }

        // Main macros
        NutritionGroup(title = stringResource(R.string.macros_str)) {
            if (facts.protein != null) {
                NutritionSimpleRow(label = stringResource(R.string.protein_str), value = facts.protein, unit = gramsUnit)
            }
            if (facts.totalFat != null) {
                NutritionSimpleRow(label = stringResource(R.string.fat_str), value = facts.totalFat, unit = gramsUnit)
            }
            if (facts.carbohydrates != null) {
                NutritionSimpleRow(label = stringResource(R.string.carbs_str), value = facts.carbohydrates, unit = gramsUnit)
            }
        }

        // Carbs detailed
        if (listOf(facts.sugars, facts.addedSugars, facts.fiber, facts.starch).any { it != null }) {
            NutritionGroup(title = stringResource(R.string.including_carbs_str)) {
                if (facts.fiber != null) {
                    NutritionSimpleRow(label = stringResource(R.string.fiber_str), value = facts.fiber, unit = gramsUnit)
                }
                if (facts.starch != null) {
                    NutritionSimpleRow(label = stringResource(R.string.starch_str), value = facts.starch, unit = gramsUnit)
                }
                if (facts.sugars != null) {
                    NutritionSimpleRow(label = stringResource(R.string.sugars_str), value = facts.sugars, unit = gramsUnit)
                }
                if (facts.addedSugars != null) {
                    NutritionSimpleRow(label = stringResource(R.string.added_sugars_str), value = facts.addedSugars, unit = gramsUnit)
                }
            }
        }

        // Fats detailed
        if (listOf(facts.saturatedFat, facts.monounsaturatedFat, facts.polyunsaturatedFat, facts.transFat).any { it != null }) {
            NutritionGroup(title = stringResource(R.string.including_fats_str)) {
                if (facts.saturatedFat != null) {
                    NutritionSimpleRow(label = stringResource(R.string.saturated_ones_str), value = facts.saturatedFat, unit = gramsUnit)
                }
                if (facts.monounsaturatedFat != null) {
                    NutritionSimpleRow(label = stringResource(R.string.monounsaturated_ones_str), value = facts.monounsaturatedFat, unit = gramsUnit)
                }
                if (facts.polyunsaturatedFat != null) {
                    NutritionSimpleRow(label = stringResource(R.string.polyunsaturated_ones_str), value = facts.polyunsaturatedFat, unit = gramsUnit)
                }
                if (facts.transFat != null) {
                    NutritionSimpleRow(label = stringResource(R.string.trans_fats_str), value = facts.transFat, unit = gramsUnit)
                }
            }
        }

        // Minerals
        if (listOf(facts.cholesterolMg, facts.sodiumMg, facts.saltG).any { it != null }) {
            NutritionGroup(title = stringResource(R.string.including_minerals_str)) {
                if (facts.cholesterolMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.cholesterol_str), value = facts.cholesterolMg, unit = stringResource(R.string.mg_unit_short))
                }
                if (facts.sodiumMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.sodium_str), value = facts.sodiumMg, unit = stringResource(R.string.mg_unit_short))
                }
                if (facts.saltG != null) {
                    NutritionSimpleRow(label = stringResource(R.string.salt_str), value = facts.saltG, unit = gramsUnit)
                }
            }
        }
    }
}

@Composable
fun NutritionGroup(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = OnboardingGlassTokens.TextTertiary
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 4.dp),
            color = OnboardingGlassTokens.GlassBorder
        )
        content()
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun ProductNutritionSectionPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        ProductNutritionSection(
            facts = previewFoodProduct().nutritionFacts
        )
    }
}