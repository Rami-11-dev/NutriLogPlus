package com.saliery.nutrilog.app.presentation.product

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductRoute(
    productId: String,
    onBackClick: () -> Unit,
    onOpenAddToMeal: (FoodProductModelLite) -> Unit,
    viewModel: ProductViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(productId) {
        viewModel.onIntent(ProductIntent.LoadProduct(productId))
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is ProductEffect.OpenAddToMeal -> onOpenAddToMeal(effect.productPreview)
                is ProductEffect.ShowMessage -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    ProductScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick,
        onIntent = viewModel::onIntent
    )
}
