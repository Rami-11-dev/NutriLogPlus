package com.saliery.nutrilog.app.presentation.recipeList


data class RecipeListState(
    val query: String = "",
    val submittedQuery: String? = null,
    val searchState: RecipeListSearchState = RecipeListSearchState.Loading
)
