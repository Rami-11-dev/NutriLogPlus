package com.saliery.nutrilog.app.presentation.productList

import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite

class ProductListReducer {

    fun reduce(
        state: ProductListState,
        intent: ProductListIntent
    ): ProductListState {

        return when (intent) {
            ProductListIntent.ClearQueryClicked -> {
                state.copy(
                    query = "",
                    submittedQuery = null,
                    searchState = ProductListSearchState.Idle
                )
            }
            is ProductListIntent.ProductClicked -> state
            is ProductListIntent.QueryChanged -> {
                state.copy(
                    query = intent.value
                )
            }
            ProductListIntent.RetryClicked -> {
                val submitted = state.submittedQuery

                if (submitted.isNullOrBlank()) {
                    state
                } else {
                    state.copy(
                        searchState = ProductListSearchState.Loading
                    )
                }
            }
            ProductListIntent.SearchSubmitted -> {
                val trimmed = state.query.trim()

                if (trimmed.isBlank()) {
                    state.copy(
                        submittedQuery = null,
                        searchState = ProductListSearchState.Idle
                    )
                } else {
                    state.copy(
                        query = trimmed,
                        submittedQuery = trimmed,
                        searchState = ProductListSearchState.Loading
                    )
                }
            }

            is ProductListIntent.RunSearch -> {
                val trimmed = intent.query.trim()

                if (trimmed.isBlank()) {
                    state.copy(
                        submittedQuery = null,
                        searchState = ProductListSearchState.Idle
                    )
                } else {
                    state.copy(
                        query = trimmed,
                        submittedQuery = trimmed,
                        searchState = ProductListSearchState.Loading
                    )
                }
            }
        }
    }

    fun onSearchSuccess(
        state: ProductListState,
        items: List<FoodProductModelLite>
    ): ProductListState {
        return state.copy(
            searchState = if (items.isEmpty()) {
                ProductListSearchState.Empty
            } else {
                ProductListSearchState.Success(items)
            }
        )
    }

    fun onSearchError(
        state: ProductListState,
        message: String
    ): ProductListState {
        return state.copy(
            searchState = ProductListSearchState.Error(message)
        )
    }
}