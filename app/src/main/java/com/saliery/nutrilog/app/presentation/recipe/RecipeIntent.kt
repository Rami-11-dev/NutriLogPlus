package com.saliery.nutrilog.app.presentation.recipe

import com.saliery.nutrilog.app.domain.model.business.CookingMethod
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.presentation.mealEntryScreen.RecipeDraft

sealed interface RecipeIntent {
    data class LoadRecipe(val recipeId: Long) : RecipeIntent
    data class LoadRecipeWithDraft(val draft: RecipeDraft) : RecipeIntent

    data class NameChanged(val value: String) : RecipeIntent
    data class ServingsYieldChanged(val value: String) : RecipeIntent

    data object AddIngredientClicked : RecipeIntent
    data class IngredientAdded(val product: FoodProductModelLite) : RecipeIntent

    data class IngredientAmountChanged(
        val productId: String,
        val value: String
    ) : RecipeIntent

    data class IngredientCookingMethodChanged(
        val productId: String,
        val method: CookingMethod
    ) : RecipeIntent

    data class RemoveIngredientClicked(val productId: String) : RecipeIntent

    data object SaveClicked : RecipeIntent
    data object DeleteClicked : RecipeIntent
    data object RetryClicked : RecipeIntent
}