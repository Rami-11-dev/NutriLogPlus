package com.saliery.nutrilog.app.presentation.productList

import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite

sealed interface ProductListSearchState {
    data object Idle : ProductListSearchState
    data object Loading : ProductListSearchState
    data object Empty : ProductListSearchState
    data class Success(
        val items: List<FoodProductModelLite>
    ) : ProductListSearchState
    data class Error(
        val message: String
    ) : ProductListSearchState
}