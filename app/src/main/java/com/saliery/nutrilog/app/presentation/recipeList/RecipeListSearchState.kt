package com.saliery.nutrilog.app.presentation.recipeList

import com.saliery.nutrilog.app.domain.model.recipe.RecipeListItemModel

sealed interface RecipeListSearchState {

    data object Loading : RecipeListSearchState
    data object Empty : RecipeListSearchState

    data class Success(
        val items: List<RecipeListItemModel>
    ) : RecipeListSearchState
    data class Error(
        val message: String
    ) : RecipeListSearchState
}