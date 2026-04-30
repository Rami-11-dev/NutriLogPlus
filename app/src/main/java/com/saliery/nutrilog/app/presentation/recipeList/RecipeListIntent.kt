package com.saliery.nutrilog.app.presentation.recipeList

sealed interface RecipeListIntent {
    data class QueryChanged(val query: String) : RecipeListIntent
    data class RecipeClicked(val recipeId: Long) : RecipeListIntent

    data object SearchSubmitted : RecipeListIntent
    data object RetryClicked : RecipeListIntent
    data object ClearQueryClicked : RecipeListIntent
}