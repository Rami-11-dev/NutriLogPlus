package com.saliery.nutrilog.app.presentation.productList

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductListRoute(
    onBackClick: () -> Unit,
    query: String?,
    onProductClick: (String) -> Unit,
    viewModel: ProductListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(query) {
        if (!query.isNullOrBlank()) {
            viewModel.onIntent(ProductListIntent.RunSearch(query))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is ProductListEffect.OpenProduct -> {
                    onProductClick(effect.productId)
                }
                is ProductListEffect.ShowMessage -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    ProductListScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick,
        onIntent = viewModel::onIntent
    )
}