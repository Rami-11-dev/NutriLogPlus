package com.saliery.nutrilog.app.presentation.recipe

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.presentation.mealEntryScreen.RecipeDraft
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RecipeRoute(
    recipeId: Long?,
    recipeDraft: RecipeDraft?,
    onBackClick: () -> Unit,
    onOpenIngredientPicker: () -> Unit,
    pickedIngredient: FoodProductModelLite?,
    onIngredientConsumed: () -> Unit,
    viewModel: RecipeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(recipeId) {
        if (recipeId != null) {
            viewModel.onIntent(RecipeIntent.LoadRecipe(recipeId))
        }
    }

    LaunchedEffect(pickedIngredient?.id) {
        pickedIngredient?.let {
            viewModel.onIntent(RecipeIntent.IngredientAdded(it))
            onIngredientConsumed()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                RecipeEffect.NavigateBack -> onBackClick()
                RecipeEffect.OpenIngredientPicker -> onOpenIngredientPicker()
                is RecipeEffect.ShowMessage -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    LaunchedEffect(recipeDraft) {
        if (recipeDraft != null) {
            viewModel.onIntent(RecipeIntent.LoadRecipeWithDraft(recipeDraft))
        }
    }

    RecipeScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick,
        onIntent = viewModel::onIntent
    )
}