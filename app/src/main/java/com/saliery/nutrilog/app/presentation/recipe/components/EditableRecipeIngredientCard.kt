package com.saliery.nutrilog.app.presentation.recipe.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.business.CookingMethod
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.domain.model.product.ProductDataSourceEnum
import com.saliery.nutrilog.app.presentation.helper.previewEditableRecipeIngredientUiModel
import com.saliery.nutrilog.app.presentation.recipe.EditableRecipeIngredientUiModel
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun EditableRecipeIngredientCard(
    ingredient: EditableRecipeIngredientUiModel,
    onAmountChanged: (String) -> Unit,
    onCookingMethodChanged: (CookingMethod) -> Unit,
    onRemoveClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = OnboardingGlassTokens.GlassSurface,
        border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IngredientThumbnail(ingredient.productPreview, Modifier.size(56.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = ingredient.productPreview.productName ?: stringResource(R.string.unknown_val_str),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = OnboardingGlassTokens.TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = ingredient.productPreview.productBrand ?: stringResource(R.string.own_product_str),
                        style = MaterialTheme.typography.bodySmall,
                        color = OnboardingGlassTokens.TextTertiary
                    )
                }

                GlassActionButton(
                    icon = Icons.Rounded.Delete,
                    onClick = onRemoveClick,
                    tint = OnboardingGlassTokens.BmiRed,
                    containerColor = OnboardingGlassTokens.BmiRed.copy(alpha = 0.1f)
                )
            }

            IngredientNutritionLine(ingredient.productPreview)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IngredientAmountStepperField(
                    value = ingredient.amountGramsInput,
                    modifier = Modifier.weight(1.2f),
                    onValueChange = onAmountChanged
                )

                CookingMethodField(
                    value = ingredient.cookingMethod,
                    onMethodSelected = onCookingMethodChanged
                )
            }
        }
    }
}

@Composable
private fun IngredientAmountStepperField(
    value: String,
    modifier: Modifier = Modifier,
    stepGrams: Int = 20,
    minGrams: Int = 0,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = stringResource(R.string.amount_str),
            style = MaterialTheme.typography.labelMedium,
            color = OnboardingGlassTokens.TextSecondary
        )

        Surface(
            shape = RoundedCornerShape(14.dp),
            color = OnboardingGlassTokens.GlassSurfaceStrong,
            border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                GlassActionButton(
                    icon = Icons.Rounded.KeyboardArrowDown,
                    onClick = {
                        val current = value.replace(',', '.').toIntOrNull() ?: 0
                        val newValue = (current - stepGrams).coerceAtLeast(minGrams)
                        onValueChange(newValue.toString())
                    },
                    modifier = Modifier.size(32.dp)
                )

                BasicTextField(
                    value = value,
                    onValueChange = { newValue ->
                        val filtered = newValue.filter { it.isDigit() }
                        onValueChange(filtered)
                    },
                    modifier = Modifier.widthIn(min = 44.dp, max = 60.dp),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = OnboardingGlassTokens.TextPrimary,
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (value.isBlank()) {
                                Text(
                                    text = "100",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = OnboardingGlassTokens.TextTertiary
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Text(
                    text = stringResource(R.string.grams_unit_short),
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnboardingGlassTokens.TextSecondary
                )

                GlassActionButton(
                    icon = Icons.Rounded.Add,
                    onClick = {
                        val current = value.replace(',', '.').toIntOrNull() ?: 0
                        val newValue = current + stepGrams
                        onValueChange(newValue.toString())
                    },
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
private fun GlassActionButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = OnboardingGlassTokens.TextPrimary,
    containerColor: Color = OnboardingGlassTokens.GlassSurface
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = containerColor,
        border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder),
        modifier = modifier.size(36.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun IngredientThumbnail(
    product: FoodProductModelLite,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(OnboardingGlassTokens.GlassSurfaceStrong),
        contentAlignment = Alignment.Center
    ) {
        if (product.displayImage != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.displayImage)
                    .crossfade(true)
                    .build(),
                contentDescription = product.productName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            val icon = when (product.source) {
                ProductDataSourceEnum.OPEN_FOOD_FACTS -> painterResource(R.drawable.open_food_facts_seeklogo__1_)
                else -> painterResource(R.drawable.usda_logo_color)
            }

            Icon(
                painter = icon,
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun IngredientNutritionLine(
    product: FoodProductModelLite
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val stats = listOf(
            Triple(
                stringResource(R.string.kcal_unit_str),
                product.caloriesPer100g?.toInt()?.toString() ?: "—",
                OnboardingGlassTokens.GlassSurfaceStrong.copy(alpha = 0.5f)
            ),
            Triple(
                stringResource(R.string.protein_unit_short_str),
                product.proteinsPer100g?.toString() ?: "—",
                OnboardingGlassTokens.ProteinColor.copy(alpha = 0.1f)
            ),
            Triple(
                stringResource(R.string.fats_unit_short_str),
                product.fatsPer100g?.toString() ?: "—",
                OnboardingGlassTokens.FatsColor.copy(alpha = 0.2f)
            ),
            Triple(
                stringResource(R.string.carbs_unit_short_str),
                product.carbsPer100g?.toString() ?: "—",
                OnboardingGlassTokens.CarbsColor.copy(alpha = 0.2f)
            )
        )

        stats.forEach { (label, value, color) ->
            Surface(
                modifier = Modifier.weight(1f),
                color = OnboardingGlassTokens.GlassSurfaceStrong.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.5.dp, color.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(label, style = MaterialTheme.typography.labelSmall, color = OnboardingGlassTokens.TextTertiary)
                    Spacer(Modifier.width(4.dp))
                    Text(value, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = OnboardingGlassTokens.TextPrimary)
                }
            }
        }
    }
}

@Composable
private fun CookingMethodField(
    value: CookingMethod,
    onMethodSelected: (CookingMethod) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = stringResource(R.string.cooking_method_str),
            style = MaterialTheme.typography.labelMedium,
            color = OnboardingGlassTokens.TextSecondary
        )

        Surface(
            onClick = { expanded = true },
            shape = RoundedCornerShape(14.dp),
            color = OnboardingGlassTokens.GlassSurfaceStrong,
            border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                CookingMethodIcon(value)

                Text(
                    text = value.toLabel(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnboardingGlassTokens.TextPrimary
                )
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = null,
                    tint = OnboardingGlassTokens.TextSecondary
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            CookingMethod.entries.forEach { method ->
                DropdownMenuItem(
                    text = {
                        Text(method.toLabel())
                    },
                    onClick = {
                        expanded = false
                        onMethodSelected(method)
                    },
                    leadingIcon = {
                        CookingMethodIcon(method)
                    }
                )
            }
        }
    }
}

@Composable
private fun CookingMethodIcon(method: CookingMethod) {
    val painter = when (method) {
        CookingMethod.RAW -> painterResource(R.drawable.leaf_eco_svgrepo_com)
        CookingMethod.BOILED -> painterResource(R.drawable.pot_svgrepo_com)
        CookingMethod.FRIED -> painterResource(R.drawable.fried_egg_and_pan_svgrepo_com)
        CookingMethod.STEAMED -> painterResource(R.drawable.kitchen_pack_steam_svgrepo_com)
        CookingMethod.BAKED -> painterResource(R.drawable.oven_svgrepo_com)
        CookingMethod.GRILLED -> painterResource(R.drawable.grill_svgrepo_com)
    }

    Icon(
        painter = painter,
        contentDescription = null,
        tint = OnboardingGlassTokens.TextSecondary,
        modifier = Modifier.size(18.dp)
    )
}

@Composable
private fun CookingMethod.toLabel(): String = when (this) {
    CookingMethod.RAW -> stringResource(R.string.raw_method_str)
    CookingMethod.BOILED -> stringResource(R.string.boiled_method_str)
    CookingMethod.FRIED -> stringResource(R.string.fried_method_str)
    CookingMethod.STEAMED -> stringResource(R.string.steamed_method_str)
    CookingMethod.BAKED -> stringResource(R.string.baked_method_str)
    CookingMethod.GRILLED -> stringResource(R.string.grilled_method_str)
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun EditableRecipeIngredientCardPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        EditableRecipeIngredientCard(
            ingredient = previewEditableRecipeIngredientUiModel()[0],
            onAmountChanged = { amount ->

            },
            onCookingMethodChanged = { method ->

            },
            onRemoveClick = {}
        )
    }
}