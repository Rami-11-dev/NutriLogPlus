package com.saliery.nutrilog.app.presentation.mealEntryScreen

import com.saliery.nutrilog.app.domain.model.entries.LocalMealType
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.domain.model.recipe.RecipeListItemModel
import java.time.LocalTime

sealed interface MealEntryIntent {
    data class LoadEntry(val entryId: Long) : MealEntryIntent

    data class MealTypeChanged(val mealType: LocalMealType) : MealEntryIntent
    data class TimeChanged(val time: LocalTime) : MealEntryIntent

    data object AddProductClicked : MealEntryIntent
    data object AddRecipeClicked : MealEntryIntent

    data class ProductAdded(val product: FoodProductModelLite) : MealEntryIntent
    data class RecipeAdded(val recipe: RecipeListItemModel) : MealEntryIntent

    data class ItemAmountChanged(
        val itemId: Long,
        val value: String
    ) : MealEntryIntent

    data class IncreaseItemAmountClicked(val itemId: Long) : MealEntryIntent
    data class DecreaseItemAmountClicked(val itemId: Long) : MealEntryIntent

    data class RemoveItemClicked(val itemId: Long) : MealEntryIntent

    data object SaveAsRecipeClicked : MealEntryIntent
    data object SaveClicked : MealEntryIntent
    data object DeleteClicked : MealEntryIntent
    data object RetryClicked : MealEntryIntent
}