package com.saliery.nutrilog.app.presentation.productList

data class ProductListState(
    val query: String = "",
    val submittedQuery: String? = null,
    val searchState: ProductListSearchState = ProductListSearchState.Idle
)
