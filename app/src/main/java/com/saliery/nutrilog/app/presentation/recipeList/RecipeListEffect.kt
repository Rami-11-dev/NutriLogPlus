package com.saliery.nutrilog.app.presentation.recipeList

sealed interface RecipeListEffect {
    data class OpenRecipe(val recipeId: Long) : RecipeListEffect
    data class ShowMessage(val message: String) : RecipeListEffect
}