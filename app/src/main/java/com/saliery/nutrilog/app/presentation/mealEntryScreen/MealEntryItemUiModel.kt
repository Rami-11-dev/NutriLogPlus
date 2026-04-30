package com.saliery.nutrilog.app.presentation.mealEntryScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.domain.model.recipe.RecipeListItemModel

sealed class MealEntryItemUiModel {
    abstract val id: Long
    abstract val amountInput: String
    abstract val amountUnit: AmountUnit

    abstract val calories: Double
    abstract val proteins: Double
    abstract val fats: Double
    abstract val carbs: Double

    data class ProductUiItem(
        override val id: Long,
        override val amountInput: String,
        override val amountUnit: AmountUnit = AmountUnit.GRAMS,

        override val calories: Double,
        override val proteins: Double,
        override val fats: Double,
        override val carbs: Double,

        val productPreview: FoodProductModelLite
    ) : MealEntryItemUiModel()

    data class RecipeUiItem(
        override val id: Long,
        override val amountInput: String,
        override val amountUnit: AmountUnit = AmountUnit.PORTIONS,

        override val calories: Double,
        override val proteins: Double,
        override val fats: Double,
        override val carbs: Double,

        val recipePreview: RecipeListItemModel
    ) : MealEntryItemUiModel()
}

enum class AmountUnit {
    GRAMS,
    PORTIONS
}

@Composable
fun AmountUnit.toDisplayText(): String = when (this) {
    AmountUnit.GRAMS -> stringResource(R.string.grams_unit_short)
    AmountUnit.PORTIONS -> stringResource(R.string.portions_short_label)
}

fun AmountUnit.defaultStep(): Double = when (this) {
    AmountUnit.GRAMS -> 20.0
    AmountUnit.PORTIONS -> 0.5
}