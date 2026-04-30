package com.saliery.nutrilog.app.presentation.product.components.contentSections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.product.FoodProductModel
import com.saliery.nutrilog.app.presentation.common.NutritionSimpleRow
import com.saliery.nutrilog.app.presentation.helper.previewFoodProduct
import com.saliery.nutrilog.app.presentation.product.components.IngredientTag
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun ProductIngredientsSection(product: FoodProductModel) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

        // ---- Block 1: Main components ----
        if (product.ingredients.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.ingredients_str),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = OnboardingGlassTokens.TextTertiary
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = OnboardingGlassTokens.GlassBorder
                )

                product.ingredients.forEach { item ->
                    NutritionSimpleRow(
                        label = item.ingredientName.replaceFirstChar { it.uppercase() },
                        value = item.percentEstimate,
                        unit = "%"
                    )
                }
            }
        }

        // ---- Block 2 TAGS: Allergens, Traces, Additives ----
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

            // Allergens
            if (product.allergens.isNotEmpty()) {
                IngredientGroup(title = stringResource(R.string.allergens_str), titleColor = OnboardingGlassTokens.BmiRed) {
                    product.allergens.forEach {
                        IngredientTag(
                            text = it,
                            containerColor = OnboardingGlassTokens.BmiRed,
                            contentColor = OnboardingGlassTokens.BmiRed
                        )
                    }
                }
            }

            // Traces
            if (product.traces.isNotEmpty()) {
                IngredientGroup(title = stringResource(R.string.traces_str), titleColor = OnboardingGlassTokens.BmiYellow) {
                    product.traces.forEach {
                        IngredientTag(
                            text = it,
                            containerColor = OnboardingGlassTokens.BmiYellow,
                            contentColor = OnboardingGlassTokens.BmiYellow
                        )
                    }
                }
            }

            // Additives
            if (product.additives.isNotEmpty()) {
                IngredientGroup(title = stringResource(R.string.additives_str)) {
                    product.additives.forEach {
                        IngredientTag(
                            text = it,
                            containerColor = OnboardingGlassTokens.GlassBorder,
                            contentColor = OnboardingGlassTokens.TextSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun IngredientGroup(
    title: String,
    titleColor: Color = OnboardingGlassTokens.TextTertiary,
    content: @Composable FlowRowScope.() -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = titleColor
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 4.dp),
            color = OnboardingGlassTokens.GlassBorder
        )
        
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = content
        )
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "ru"
)
@Composable
private fun ProductIngredientsSectionPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        ProductIngredientsSection(
            product = previewFoodProduct()
        )
    }
}