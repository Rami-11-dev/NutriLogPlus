package com.saliery.nutrilog.app.presentation.product.components.contentSections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.product.MicronutrientModel
import com.saliery.nutrilog.app.presentation.common.NutritionSimpleRow
import com.saliery.nutrilog.app.presentation.helper.previewFoodProduct
import com.saliery.nutrilog.ui.theme.NutriLogTheme

@Composable
fun ProductMicronutrientsSection(
    micro: MicronutrientModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val mgUnit = stringResource(R.string.mg_unit_short)
        val mcgUnit = stringResource(R.string.mcg_unit_str)
        val gUnit = stringResource(R.string.grams_unit_short)

        // === MACROMINERALS ===
        if (listOf(
                micro.potassiumMg,
                micro.calciumMg,
                micro.magnesiumMg,
                micro.phosphorusMg,
                micro.ironMg
            ).any { it != null }) {
            NutritionGroup(title = stringResource(R.string.minerals_str)) {
                if (micro.potassiumMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.potassium_str), value = micro.potassiumMg, unit = mgUnit)
                }
                if (micro.calciumMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.calcium_str), value = micro.calciumMg, unit = mgUnit)
                }
                if (micro.magnesiumMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.magnesium_str), value = micro.magnesiumMg, unit = mgUnit)
                }
                if (micro.phosphorusMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.phosphorus_str), value = micro.phosphorusMg, unit = mgUnit)
                }
                if (micro.ironMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.ferum_str), value = micro.ironMg, unit = mgUnit)
                }
            }
        }

        // === MICROELEMENTS ===
        if (listOf(
                micro.zincMg,
                micro.seleniumMcg,
                micro.copperMg,
                micro.manganeseMg
            ).any { it != null }) {
            NutritionGroup(title = stringResource(R.string.microelements_str)) {
                if (micro.zincMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.zinc_str), value = micro.zincMg, unit = mgUnit)
                }
                if (micro.seleniumMcg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.selenium_str), value = micro.seleniumMcg, unit = mcgUnit)
                }
                if (micro.copperMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.copper_str), value = micro.copperMg, unit = mgUnit)
                }
                if (micro.manganeseMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.manganese_str), value = micro.manganeseMg, unit = mgUnit)
                }
            }
        }

        // === B-VITAMINS ===
        if (listOf(
                micro.vitaminB1Mg,
                micro.vitaminB2Mg,
                micro.vitaminB3Mg,
                micro.vitaminB5Mg,
                micro.vitaminB6Mg,
                micro.vitaminB12Mcg
            ).any { it != null }) {
            NutritionGroup(title = stringResource(R.string.b_vitamins_str)) {
                if (micro.vitaminB1Mg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.vitamin_str, "B1"), value = micro.vitaminB1Mg, unit = mgUnit)
                }
                if (micro.vitaminB2Mg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.vitamin_str, "B2"), value = micro.vitaminB2Mg, unit = mgUnit)
                }
                if (micro.vitaminB3Mg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.vitamin_str, "B3"), value = micro.vitaminB3Mg, unit = mgUnit)
                }
                if (micro.vitaminB5Mg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.vitamin_str, "B5"), value = micro.vitaminB5Mg, unit = mgUnit)
                }
                if (micro.vitaminB6Mg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.vitamin_str, "B6"), value = micro.vitaminB6Mg, unit = mgUnit)
                }
                if (micro.vitaminB12Mcg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.vitamin_str, "B12"), value = micro.vitaminB12Mcg, unit = mcgUnit)
                }
            }
        }

        // === FAT-SOLUBLE VITAMINS ===
        if (listOf(
                micro.vitaminAMcg,
                micro.vitaminDMcg,
                micro.vitaminEMg,
                micro.vitaminKMcg
            ).any { it != null }) {
            NutritionGroup(title = stringResource(R.string.fat_soluble_vitamins_str)) {
                if (micro.vitaminAMcg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.vitamin_str, "A"), value = micro.vitaminAMcg, unit = mcgUnit)
                }
                if (micro.vitaminDMcg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.vitamin_str, "D"), value = micro.vitaminDMcg, unit = mcgUnit)
                }
                if (micro.vitaminEMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.vitamin_str, "E"), value = micro.vitaminEMg, unit = mgUnit)
                }
                if (micro.vitaminKMcg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.vitamin_str, "K"), value = micro.vitaminKMcg, unit = mcgUnit)
                }
            }
        }

        // === VITAMIN C ===
        if (micro.vitaminCMg != null) {
            NutritionGroup(title = stringResource(R.string.vitamin_str, "C")) {
                NutritionSimpleRow(label = stringResource(R.string.vitamin_str, "C"), value = micro.vitaminCMg, unit = mgUnit)
            }
        }

        // === FOLATE (разделен) ===
        if (listOf(
                micro.folateTotalMcg,
                micro.folateFoodMcg,
                micro.folicAcidMcg
            ).any { it != null }) {
            NutritionGroup(title = stringResource(R.string.folate_str)) {
                if (micro.folateTotalMcg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.folate_total_str), value = micro.folateTotalMcg, unit = mcgUnit)
                }
                if (micro.folateFoodMcg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.folate_food_str), value = micro.folateFoodMcg, unit = mcgUnit)
                }
                if (micro.folicAcidMcg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.folic_acid_str), value = micro.folicAcidMcg, unit = mcgUnit)
                }
            }
        }

        // === ACTIVE ELEMENTS ===
        if (listOf(
                micro.caffeineMg,
                micro.cholineMg
            ).any { it != null }) {
            NutritionGroup(title = stringResource(R.string.active_elements_str)) {
                if (micro.caffeineMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.caffeine_str), value = micro.caffeineMg, unit = mgUnit)
                }
                if (micro.cholineMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.choline_str), value = micro.cholineMg, unit = mgUnit)
                }
            }
        }

        // === ANTIOXIDANTS & OMEGA-3 ===
        if (listOf(
                micro.luteinZeaxanthinMcg,
                micro.epaMg,
                micro.dhaMg
            ).any { it != null }) {
            NutritionGroup(title = stringResource(R.string.antioxidants_str)) {
                if (micro.luteinZeaxanthinMcg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.lutein_zeaxanthin_str), value = micro.luteinZeaxanthinMcg, unit = mcgUnit)
                }
                if (micro.epaMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.epa_str), value = micro.epaMg, unit = mgUnit)
                }
                if (micro.dhaMg != null) {
                    NutritionSimpleRow(label = stringResource(R.string.dha_str), value = micro.dhaMg, unit = mgUnit)
                }
            }
        }

        // === OTHER COMPONENTS ===
        if (listOf(
                micro.waterG,
                micro.alcoholG,
                micro.ashG
            ).any { it != null }) {
            NutritionGroup(title = stringResource(R.string.other_components_str)) {
                if (micro.waterG != null) {
                    NutritionSimpleRow(label = stringResource(R.string.water_str), value = micro.waterG, unit = gUnit)
                }
                if (micro.alcoholG != null) {
                    NutritionSimpleRow(label = stringResource(R.string.alcohol_str), value = micro.alcoholG, unit = gUnit)
                }
                if (micro.ashG != null) {
                    NutritionSimpleRow(label = stringResource(R.string.ash_str), value = micro.ashG, unit = gUnit)
                }
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "ru"
)
@Composable
private fun ProductMicronutrientSectionPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        ProductMicronutrientsSection(
            micro = previewFoodProduct().micronutrients!!
        )
    }
}