package com.saliery.nutrilog.app.presentation.home

import android.annotation.SuppressLint
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
import com.saliery.nutrilog.app.presentation.helper.previewHomeState
import com.saliery.nutrilog.app.presentation.helper.previewUser
import com.saliery.nutrilog.app.presentation.home.components.HomeBottomNavigationBar
import com.saliery.nutrilog.app.presentation.home.components.HomeDestination
import com.saliery.nutrilog.app.presentation.home.components.HomeTopAppBar
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun HomeScreen(
    state: HomeState,
    snackbarHostState: SnackbarHostState,
    onIntent: (HomeIntent) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToRecipeList: () -> Unit,
    onNavigateToMealJournal: () -> Unit
) {
    val hazeState = rememberHazeState()

    Scaffold(
        topBar = {
            HomeTopAppBar(
                query = state.searchQuery,
                placeholder = stringResource(R.string.search_a_product_str),
                hazeState = hazeState,
                onClearClick = { onIntent(HomeIntent.ClearSearchClicked) },
                onSearchClick = { onIntent(HomeIntent.SearchSubmitted) },
                onOpenCameraClick = { onIntent(HomeIntent.OpenCameraClicked) },
                onQueryChange = { onIntent(HomeIntent.SearchQueryChanged(it)) }
            )
        },
        bottomBar = {
            HomeBottomNavigationBar(
                hazeState = hazeState,
                currentRoute = HomeDestination,
                onNavigateToHome = onNavigateToHome,
                onNavigateToRecipeList = onNavigateToRecipeList,
                onNavigateToMealJournal = onNavigateToMealJournal,
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
            HomeContent(
                state = state,
                paddingValues = innerPadding,
                hazeState = hazeState,
                onIntent = onIntent
            )
        }
    }
}

@SuppressLint("RememberReturnType")
@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun HomeScreenPreview() {

    val state = remember { previewHomeState() }

    NutriLogTheme(
        darkTheme = true
    ) {
        HomeScreen(
            state = state,
            snackbarHostState = SnackbarHostState(),
            onIntent = {},
            onNavigateToHome = {},
            onNavigateToRecipeList = {},
            onNavigateToMealJournal = {}
        )
    }
}