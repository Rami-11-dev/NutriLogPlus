package com.saliery.nutrilog.app.presentation.recipeList

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.saliery.nutrilog.app.presentation.common.ScreenListTopBar
import com.saliery.nutrilog.app.presentation.helper.previewRecipeList
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun RecipeListScreen(
    state: RecipeListState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onIntent: (RecipeListIntent) -> Unit
) {

    val hazeState = rememberHazeState()

    Scaffold(
        topBar = {
            ScreenListTopBar(
                query = state.query,
                placeholder = "Try to type something",
                hazeState = hazeState,
                onBackClick = onBackClick,
                onQueryChange = { onIntent(RecipeListIntent.QueryChanged(it)) },
                onClearClick = { onIntent(RecipeListIntent.ClearQueryClicked) },
                onSearchClick = { onIntent(RecipeListIntent.SearchSubmitted) }
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
            RecipeListContent(
                state = state,
                hazeState = hazeState,
                paddingValues = innerPadding,
                onIntent = onIntent
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun RecipeListScreenPreview() {

    val state = remember { RecipeListState() }

    NutriLogTheme(
        darkTheme = true
    ) {
        RecipeListScreen(
            state = state.copy(
                searchState = RecipeListSearchState.Success(
                    items = previewRecipeList()
                )
            ),
            snackbarHostState = SnackbarHostState(),
            onBackClick = {},
            onIntent = {}
        )
    }
}