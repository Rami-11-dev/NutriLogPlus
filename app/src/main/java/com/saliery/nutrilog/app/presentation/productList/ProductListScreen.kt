package com.saliery.nutrilog.app.presentation.productList

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.presentation.common.ScreenListTopBar
import com.saliery.nutrilog.app.presentation.helper.previewFoodProductModelLiteList
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun ProductListScreen(
    state: ProductListState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onIntent: (ProductListIntent) -> Unit
) {

    val hazeState = rememberHazeState()

    Scaffold(
        topBar = {
            ScreenListTopBar(
                query = state.query,
                placeholder = stringResource(R.string.search_a_product_str),
                hazeState = hazeState,
                onBackClick = onBackClick,
                onQueryChange = { onIntent(ProductListIntent.QueryChanged(it)) },
                onClearClick = { onIntent(ProductListIntent.ClearQueryClicked) },
                onSearchClick = { onIntent(ProductListIntent.SearchSubmitted) }
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
                .background(
                    brush = Brush.verticalGradient(
                        colors = if (isSystemInDarkTheme()) {
                            listOf(
                                OnboardingGlassTokens.ScreenBackground,
                                OnboardingGlassTokens.ScreenBackground
                            )
                        } else {
                            listOf(
                                OnboardingGlassTokens.ScreenBackground,
                                OnboardingGlassTokens.GlassSurface.copy(alpha = 0.35f)
                            )
                        }
                    )
                )
        ) {
            ProductListContent(
                state = state,
                contentPadding = innerPadding,
                hazeState = hazeState,
                onIntent = onIntent
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun ProductListScreenPreview() {

    val state = remember { ProductListState() }

    NutriLogTheme(
        darkTheme = true
    ) {
        ProductListScreen(
            state = state.copy(
                searchState = ProductListSearchState.Success(
                    items = previewFoodProductModelLiteList()
                )
            ),
            snackbarHostState = SnackbarHostState(),
            onBackClick = {},
            onIntent = {}
        )
    }
}