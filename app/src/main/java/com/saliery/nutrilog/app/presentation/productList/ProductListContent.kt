package com.saliery.nutrilog.app.presentation.productList

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.saliery.nutrilog.app.presentation.common.ScreenErrorContent
import com.saliery.nutrilog.app.presentation.common.ScreenLoadingContent
import com.saliery.nutrilog.app.presentation.productList.components.ProductListIdleContent
import com.saliery.nutrilog.app.presentation.productList.components.ProductListResultsContent
import com.saliery.nutrilog.app.presentation.common.ScreenListEmptyContent
import dev.chrisbanes.haze.HazeState

@Composable
fun ProductListContent(
    state: ProductListState,
    contentPadding: PaddingValues,
    hazeState: HazeState,
    onIntent: (ProductListIntent) -> Unit
) {

    when (val searchState = state.searchState) {
        ProductListSearchState.Idle -> {
            ProductListIdleContent(
                contentPadding = contentPadding
            )
        }

        ProductListSearchState.Loading -> {
            ScreenLoadingContent(
                contentPadding = contentPadding
            )
        }

        ProductListSearchState.Empty -> {
            ScreenListEmptyContent(
                query = state.submittedQuery.orEmpty(),
                contentPadding = contentPadding,
                onRetryClick = {
                    onIntent(ProductListIntent.RetryClicked)
                }
            )
        }

        is ProductListSearchState.Error -> {
            ScreenErrorContent(
                message = searchState.message,
                contentPadding = contentPadding,
                onRetryClick = {
                    onIntent(ProductListIntent.RetryClicked)
                }
            )
        }

        is ProductListSearchState.Success -> {
            ProductListResultsContent(
                items = searchState.items,
                contentPadding = contentPadding,
                hazeState = hazeState,
                onProductClick = { productId ->
                    onIntent(ProductListIntent.ProductClicked(productId))
                }
            )
        }
    }
}