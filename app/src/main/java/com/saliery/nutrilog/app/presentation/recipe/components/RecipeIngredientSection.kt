package com.saliery.nutrilog.app.presentation.recipe.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.business.CookingMethod
import com.saliery.nutrilog.app.presentation.common.GlassCard
import com.saliery.nutrilog.app.presentation.helper.previewEditableRecipeIngredientUiModel
import com.saliery.nutrilog.app.presentation.recipe.EditableRecipeIngredientUiModel
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun RecipeIngredientsSection(
    ingredients: List<EditableRecipeIngredientUiModel>,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    onAddIngredientClick: () -> Unit,
    onAmountChanged: (String, String) -> Unit,
    onCookingMethodChanged: (String, CookingMethod) -> Unit,
    onRemoveIngredientClick: (String) -> Unit
) {
    GlassCard(
        modifier = modifier.fillMaxWidth(),
        hazeState = hazeState,
        contentPadding = PaddingValues(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Section header with "Add" button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.ingredients_str),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = OnboardingGlassTokens.TextPrimary
                        )

                        if (ingredients.isNotEmpty()) {
                            Spacer(Modifier.width(8.dp))
                            Surface(
                                shape = CircleShape,
                                color = OnboardingGlassTokens.Primary.copy(alpha = 0.12f),
                                border = BorderStroke(1.dp, OnboardingGlassTokens.Primary.copy(alpha = 0.2f))
                            ) {
                                Text(
                                    text = ingredients.size.toString(),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = OnboardingGlassTokens.Primary
                                )
                            }
                        }
                    }

                    Text(
                        text = if (ingredients.isEmpty())
                            stringResource(R.string.you_can_add_products_and_specify_them)
                        else
                            stringResource(R.string.you_can_specify_added_products_str),
                        style = MaterialTheme.typography.bodySmall,
                        color = OnboardingGlassTokens.TextTertiary
                    )
                }

                IconButton(
                    onClick = onAddIngredientClick,
                    modifier = Modifier
                        .size(40.dp)
                        .background(OnboardingGlassTokens.PrimarySoft, CircleShape)
                        .border(1.dp, OnboardingGlassTokens.Primary.copy(alpha = 0.3f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        tint = OnboardingGlassTokens.Primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Ingredients list
            if (ingredients.isEmpty()) {
                RecipeIngredientsEmptyState()
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    ingredients.forEach { ingredient ->
                        EditableRecipeIngredientCard(
                            ingredient = ingredient,
                            onAmountChanged = { value -> onAmountChanged(ingredient.productPreview.id, value) },
                            onCookingMethodChanged = { method -> onCookingMethodChanged(ingredient.productPreview.id, method) },
                            onRemoveClick = { onRemoveIngredientClick(ingredient.productPreview.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun RecipeIngredientsSectionPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        RecipeIngredientsSection(
            ingredients = previewEditableRecipeIngredientUiModel(),
            hazeState = rememberHazeState(),
            onAddIngredientClick = {},
            onAmountChanged = { productId, value ->

            },
            onCookingMethodChanged = { productId, method ->

            },
            onRemoveIngredientClick = { productId ->

            }
        )
    }
}