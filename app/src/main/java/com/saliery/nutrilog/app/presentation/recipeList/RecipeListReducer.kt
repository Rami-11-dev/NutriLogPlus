package com.saliery.nutrilog.app.presentation.recipeList

import com.saliery.nutrilog.app.domain.model.recipe.RecipeListItemModel

class RecipeListReducer {

    fun reduce(
        state: RecipeListState,
        intent: RecipeListIntent
    ): RecipeListState {

        return when (intent) {
            RecipeListIntent.ClearQueryClicked -> {
                state.copy(
                    query = "",
                    submittedQuery = null,
                    searchState = RecipeListSearchState.Loading
                )
            }
            is RecipeListIntent.QueryChanged -> {
                state.copy(
                    query = intent.query
                )
            }
            is RecipeListIntent.RecipeClicked -> state
            RecipeListIntent.RetryClicked -> {
                state.copy(
                    searchState = RecipeListSearchState.Loading
                )
            }
            RecipeListIntent.SearchSubmitted -> {
                val trimmed = state.query.trim()

                if (trimmed.isBlank()) {
                    state.copy(
                        submittedQuery = null,
                        searchState = RecipeListSearchState.Loading
                    )
                } else {
                    state.copy(
                        query = trimmed,
                        submittedQuery = trimmed,
                        searchState = RecipeListSearchState.Loading
                    )
                }
            }
        }
    }

    fun onSearchSuccess(
        state: RecipeListState,
        items: List<RecipeListItemModel>
    ): RecipeListState {
        return state.copy(
            searchState = if (items.isEmpty()) {
                RecipeListSearchState.Empty
            } else {
                RecipeListSearchState.Success(items)
            }
        )
    }

    fun onSearchError(
        state: RecipeListState,
        message: String
    ): RecipeListState {
        return state.copy(
            searchState = RecipeListSearchState.Error(message)
        )
    }
}