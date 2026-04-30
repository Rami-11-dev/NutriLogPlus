package com.saliery.nutrilog.app.presentation.home

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreenRoute(
    onNavigateToHome: () -> Unit,
    onNavigateToRecipeList: () -> Unit,
    onNavigateToMealEntry: () -> Unit,
    onNavigateToCamera: () -> Unit,
    onNavigateToProductList: (String) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                HomeEffect.NavigateToCamera -> {
                    onNavigateToCamera()
                }
                HomeEffect.NavigateToMealEntry -> {
                    onNavigateToMealEntry()
                }
                is HomeEffect.NavigateToProductSearch -> {
                    onNavigateToProductList(effect.query)
                }
                HomeEffect.NavigateToRecipes -> {
                    onNavigateToRecipeList()
                }
                is HomeEffect.ShowMessage -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    HomeScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onIntent = viewModel::onIntent,
        onNavigateToHome = onNavigateToHome,
        onNavigateToRecipeList = onNavigateToRecipeList,
        onNavigateToMealJournal = onNavigateToMealEntry,
    )
}