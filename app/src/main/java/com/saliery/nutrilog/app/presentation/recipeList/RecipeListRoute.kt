package com.saliery.nutrilog.app.presentation.recipeList

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RecipeListRoute(
    onBackClick: () -> Unit,
    onRecipeClick: (Long) -> Unit,
    viewModel: RecipeListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is RecipeListEffect.OpenRecipe -> {
                    onRecipeClick(effect.recipeId)
                }
                is RecipeListEffect.ShowMessage -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    RecipeListScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick,
        onIntent = viewModel::onIntent
    )
}