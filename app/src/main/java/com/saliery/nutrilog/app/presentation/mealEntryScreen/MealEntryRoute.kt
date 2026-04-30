package com.saliery.nutrilog.app.presentation.mealEntryScreen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.domain.model.recipe.RecipeListItemModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MealEntryRoute(
    entryId: Long?,
    pickedProduct: FoodProductModelLite?,
    pickedRecipe: RecipeListItemModel?,
    onPickedProductConsumed: () -> Unit,
    onPickedRecipeConsumed: () -> Unit,
    onBackClick: () -> Unit,
    onOpenProductPicker: () -> Unit,
    onOpenRecipePicker: () -> Unit,
    onOpenSaveAsRecipe: () -> Unit,
    onOpenRecipeDraft: (RecipeDraft) -> Unit,
    viewModel: MealEntryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(entryId) {
        if (entryId != null) {
            viewModel.onIntent(MealEntryIntent.LoadEntry(entryId))
        }
    }

    LaunchedEffect(pickedProduct) {
        pickedProduct?.let {
            viewModel.onIntent(MealEntryIntent.ProductAdded(it))
            onPickedProductConsumed()
        }
    }

    LaunchedEffect(pickedRecipe?.id) {
        pickedRecipe?.let {
            viewModel.onIntent(MealEntryIntent.RecipeAdded(it))
            onPickedRecipeConsumed()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                MealEntryEffect.NavigateBack -> onBackClick()
                MealEntryEffect.OpenProductPicker -> onOpenProductPicker()
                MealEntryEffect.OpenRecipePicker -> onOpenRecipePicker()
                MealEntryEffect.OpenSaveAsRecipe -> onOpenSaveAsRecipe()
                is MealEntryEffect.ShowMessage -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is MealEntryEffect.OpenRecipeWithDraft -> {
                    onOpenRecipeDraft(effect.draft)
                }
            }
        }
    }

    MealEntryScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick,
        onIntent = viewModel::onIntent
    )
}