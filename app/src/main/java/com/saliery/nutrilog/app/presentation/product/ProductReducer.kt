package com.saliery.nutrilog.app.presentation.product

import com.saliery.nutrilog.app.domain.model.product.FoodProductModel

class ProductReducer {

    fun reduce(
        state: ProductState,
        intent: ProductIntent
    ): ProductState {

        return when (intent) {
            ProductIntent.AddToMealClicked -> state
            is ProductIntent.LoadProduct -> {
                state.copy(
                    productId = intent.productId,
                    isLoading = true,
                    errorMessage = null
                )
            }
            ProductIntent.RefreshClicked -> {
                state.copy(
                    isRefreshing = true,
                    errorMessage = null
                )
            }
            ProductIntent.RetryClicked -> {
                state.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            is ProductIntent.SectionToggled -> {
                val newSections = state.expandedSections.toMutableSet().apply {
                    if (contains(intent.section)) remove(intent.section)
                    else add(intent.section)
                }

                state.copy(
                    expandedSections = newSections
                )
            }
            ProductIntent.ToggleFavoriteClicked -> state
        }
    }

    fun onLoadSuccess(
        state: ProductState,
        product: FoodProductModel
    ): ProductState {

        return state.copy(
            isLoading = false,
            isRefreshing = false,
            product = product,
            errorMessage = null
        )
    }

    fun onLoadError(
        state: ProductState,
        message: String
    ): ProductState {

        return state.copy(
            isLoading = false,
            isRefreshing = false,
            errorMessage = message
        )
    }

    fun onProductNotFound(state: ProductState): ProductState {
        return state.copy(
            isLoading = false,
            isRefreshing = false,
            isProductNotFound = true,
            errorMessage = null,
            product = null
        )
    }
}