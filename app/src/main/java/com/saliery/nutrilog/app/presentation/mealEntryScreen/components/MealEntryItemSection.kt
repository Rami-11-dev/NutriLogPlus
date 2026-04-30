package com.saliery.nutrilog.app.presentation.mealEntryScreen.components

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.presentation.common.GlassCard
import com.saliery.nutrilog.app.presentation.helper.previewMealEntryList
import com.saliery.nutrilog.app.presentation.helper.trimSmart
import com.saliery.nutrilog.app.presentation.mealEntryScreen.MealEntryItemUiModel
import com.saliery.nutrilog.app.presentation.mealEntryScreen.toDisplayText
import com.saliery.nutrilog.app.presentation.product.components.MacroElement
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun MealEntryItemsSection(
    items: List<MealEntryItemUiModel>,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    onAddProductClick: () -> Unit,
    onAddRecipeClick: () -> Unit,
    onAmountChanged: (Long, String) -> Unit,
    onIncreaseClick: (Long) -> Unit,
    onDecreaseClick: (Long) -> Unit,
    onRemoveClick: (Long) -> Unit
) {
    GlassCard(
        modifier = modifier.fillMaxWidth(),
        hazeState = hazeState,
        contentPadding = PaddingValues(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.nutrition_log_title_str),
                    style = MaterialTheme.typography.titleMedium,
                    color = OnboardingGlassTokens.TextPrimary
                )

                Text(
                    text = stringResource(R.string.you_can_add_both_products_recipes_str),
                    style = MaterialTheme.typography.bodySmall,
                    color = OnboardingGlassTokens.TextTertiary
                )
            }

            if (items.isEmpty()) {
                MealEntryItemsEmptyState()
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items.forEach { item ->
                        MealEntryItemCard(
                            item = item,
                            onAmountChange = { value ->
                                onAmountChanged(item.id, value)
                            },
                            onIncrease = {
                                onIncreaseClick(item.id)
                            },
                            onDecrease = {
                                onDecreaseClick(item.id)
                            },
                            onRemove = {
                                onRemoveClick(item.id)
                            },
                            hazeState = hazeState
                        )
                    }
                }
            }

            MealEntryAddButtonsRow(
                onAddProductClick = onAddProductClick,
                onAddRecipeClick = onAddRecipeClick
            )
        }
    }
}

@Composable
private fun MealEntryItemsEmptyState() {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = OnboardingGlassTokens.GlassSurface,
        border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.box_open_svgrepo_com),
                contentDescription = null,
                tint = OnboardingGlassTokens.TextSecondary,
                modifier = Modifier
                    .size(72.dp)
            )

            Text(
                text = stringResource(R.string.nothing_added_yet_str),
                style = MaterialTheme.typography.bodyMedium,
                color = OnboardingGlassTokens.TextSecondary
            )
        }
    }
}

@Composable
fun MealEntryAddButtonsRow(
    onAddProductClick: () -> Unit,
    onAddRecipeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AddActionButton(
            text = stringResource(R.string.own_product_str),
            icon = painterResource(R.drawable.apple_6_svgrepo_com),
            onClick = onAddProductClick,
            modifier = Modifier.weight(1f)
        )

        AddActionButton(
            text = stringResource(R.string.recipe_str),
            icon = painterResource(R.drawable.recipe_keeper_svgrepo_com),
            onClick = onAddRecipeClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun AddActionButton(
    text: String,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = OnboardingGlassTokens.GlassSurfaceStrong,
        border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder),
        modifier = modifier.height(52.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = OnboardingGlassTokens.Primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                ),
                color = OnboardingGlassTokens.TextPrimary
            )
        }
    }
}

@Composable
fun MealEntryItemCard(
    item: MealEntryItemUiModel,
    hazeState: HazeState,
    onAmountChange: (String) -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    val title = when (item) {
        is MealEntryItemUiModel.ProductUiItem -> item.productPreview.productName ?: "Продукт"
        is MealEntryItemUiModel.RecipeUiItem -> item.recipePreview.name
    }

    val brand = (item as? MealEntryItemUiModel.ProductUiItem)?.productPreview?.productBrand

    val gramsUnit = stringResource(R.string.grams_unit_short)

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = OnboardingGlassTokens.GlassSurface),
        border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = OnboardingGlassTokens.TextPrimary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (!brand.isNullOrBlank()) {
                        Text(
                            text = brand,
                            style = MaterialTheme.typography.labelMedium,
                            color = OnboardingGlassTokens.TextTertiary
                        )
                    }
                }

                GlassActionButton(
                    icon = painterResource(R.drawable.cross_svgrepo_com),
                    onClick = onRemove,
                    containerColor = OnboardingGlassTokens.BmiRed.copy(alpha = 0.1f),
                    tint = OnboardingGlassTokens.TextTertiary
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    Text(
                        text = item.calories.toInt().toString(),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                        color = OnboardingGlassTokens.Primary
                    )
                    Text(
                        text = stringResource(R.string.kcal_unit_str),
                        style = MaterialTheme.typography.labelSmall,
                        color = OnboardingGlassTokens.TextTertiary
                    )
                }

                VerticalDivider(Modifier.height(24.dp), color = OnboardingGlassTokens.GlassBorder)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MacroElement(
                        label = stringResource(R.string.protein_str),
                        value = "${item.proteins.trimSmart()} $gramsUnit",
                        accentColor = OnboardingGlassTokens.ProteinColor
                    )
                    MacroElement(
                        label = stringResource(R.string.fat_str),
                        value = "${item.fats.trimSmart()} $gramsUnit",
                        accentColor = OnboardingGlassTokens.FatsColor
                    )
                    MacroElement(
                        label = stringResource(R.string.carbs_str),
                        value = "${item.carbs.trimSmart()} $gramsUnit",
                        accentColor = OnboardingGlassTokens.CarbsColor
                    )
                }
            }

            AmountStepper(
                value = item.amountInput,
                unit = item.amountUnit.toDisplayText(),
                onValueChange = onAmountChange,
                onIncrease = onIncrease,
                onDecrease = onDecrease
            )
        }
    }
}

@Composable
fun AmountStepper(
    value: String,
    unit: String,
    onValueChange: (String) -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = OnboardingGlassTokens.GlassSurfaceStrong,
        border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder),
        modifier = Modifier.fillMaxWidth().height(48.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(6.dp))

            GlassActionButton(
                icon = painterResource(R.drawable.minus_svgrepo_com),
                onClick = onDecrease,

            )

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        color = OnboardingGlassTokens.TextPrimary,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.width(IntrinsicSize.Min),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    cursorBrush = SolidColor(OnboardingGlassTokens.Primary)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnboardingGlassTokens.TextSecondary
                )
            }

            GlassActionButton(
                icon = painterResource(R.drawable.plus_svgrepo_com),
                onClick = onIncrease
            )

            Spacer(modifier = Modifier.width(6.dp))
        }
    }
}

@Composable
fun GlassActionButton(
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = OnboardingGlassTokens.TextPrimary,
    containerColor: Color = OnboardingGlassTokens.GlassSurface.copy(alpha = 0.5f),
    hazeState: HazeState? = null,
    buttonSize: Dp = 32.dp
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = containerColor,
        border = BorderStroke(0.5.dp, OnboardingGlassTokens.GlassBorder.copy(alpha = 0.5f)),
        modifier = modifier
            .size(buttonSize)
            .then(
                if (hazeState != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Modifier.hazeEffect(
                        state = hazeState,
                        style = HazeStyle(
                            blurRadius = 18.dp,
                            tint = HazeTint(OnboardingGlassTokens.GlassSurfaceStrong.copy(alpha = 0.2f))
                        )
                    )
                } else Modifier
            )
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun MealEntryItemsSectionPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        MealEntryItemsSection(
            items = previewMealEntryList(),
            hazeState = rememberHazeState(),
            onAddProductClick = {},
            onAddRecipeClick = {},
            onAmountChanged = { amount, value ->

            },
            onIncreaseClick = {},
            onDecreaseClick = {},
            onRemoveClick = {}
        )
    }
}