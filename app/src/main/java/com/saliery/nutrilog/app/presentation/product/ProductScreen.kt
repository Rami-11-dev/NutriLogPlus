package com.saliery.nutrilog.app.presentation.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.saliery.nutrilog.app.presentation.helper.previewFoodProduct
import com.saliery.nutrilog.app.presentation.product.components.ProductBottomActionBar
import com.saliery.nutrilog.app.presentation.product.components.ProductTopBar
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun ProductScreen(
    state: ProductState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onIntent: (ProductIntent) -> Unit
) {

    val hazeState = rememberHazeState()

    Scaffold(
        topBar = {
            ProductTopBar(
                title = state.product?.name ?: "Product",
                isRefreshing = state.isRefreshing,
                isFavorite = state.product?.isFavorite ?: false,
                onBackClick = onBackClick,
                hazeState = hazeState,
                onRefreshClick = { onIntent(ProductIntent.RefreshClicked) },
                onFavoriteClick = { onIntent(ProductIntent.ToggleFavoriteClicked) }
            )
        },
        bottomBar = {
            ProductBottomActionBar(
                enabled = state.product != null && !state.isLoading,
                hazeState = hazeState,
                onAddToMealClick = { onIntent(ProductIntent.AddToMealClicked) }
            )
        },
        containerColor = OnboardingGlassTokens.ScreenBackground,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState)
        ) {
            ProductContent(
                state = state,
                contentPadding = innerPadding,
                hazeState = hazeState,
                onIntent = onIntent
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    locale = "en",
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
fun ProductScreenPreview() {

    val state = remember {
        ProductState(
            product = previewFoodProduct(),
            isLoading = false,
            isRefreshing = false
        )
    }

    NutriLogTheme(
        darkTheme = true
    ) {
        ProductScreen(
            state = state,
            snackbarHostState = remember { SnackbarHostState() },
            onBackClick = {},
            onIntent = {}
        )
    }
}