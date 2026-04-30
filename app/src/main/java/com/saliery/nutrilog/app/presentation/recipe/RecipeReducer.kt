package com.saliery.nutrilog.app.presentation.recipe

import com.saliery.nutrilog.app.domain.model.recipe.RecipeModel
import com.saliery.nutrilog.app.presentation.helper.trimSmart
import com.saliery.nutrilog.app.presentation.mealEntryScreen.RecipeDraft
import com.saliery.nutrilog.app.presentation.mealEntryScreen.toRecipeModel
import com.saliery.nutrilog.app.presentation.recipe.helper.toEditableIngredientUi
import com.saliery.nutrilog.app.presentation.recipe.helper.toEditableUi

class RecipeReducer {

    fun reduce(
        state: RecipeState,
        intent: RecipeIntent
    ): RecipeState {
        return when (intent) {
            is RecipeIntent.LoadRecipe -> {
                state.copy(
                    recipeId = intent.recipeId,
                    isNewRecipe = false,
                    isLoading = true,
                    errorMessage = null
                )
            }

            is RecipeIntent.NameChanged -> {
                state.copy(
                    nameInput = intent.value,
                    hasChanges = true
                )
            }

            is RecipeIntent.ServingsYieldChanged -> {
                state.copy(
                    servingsYieldInput = intent.value,
                    hasChanges = true
                )
            }

            RecipeIntent.AddIngredientClicked -> state

            is RecipeIntent.IngredientAdded -> {
                state.copy(
                    ingredients = state.ingredients + intent.product.toEditableIngredientUi(),
                    hasChanges = true
                )
            }

            is RecipeIntent.IngredientAmountChanged -> {
                state.copy(
                    ingredients = state.ingredients.map { ingredient ->
                        if (ingredient.productPreview.id == intent.productId) {
                            ingredient.copy(amountGramsInput = intent.value)
                        } else {
                            ingredient
                        }
                    },
                    hasChanges = true
                )
            }

            is RecipeIntent.IngredientCookingMethodChanged -> {
                state.copy(
                    ingredients = state.ingredients.map { ingredient ->
                        if (ingredient.productPreview.id == intent.productId) {
                            ingredient.copy(cookingMethod = intent.method)
                        } else {
                            ingredient
                        }
                    },
                    hasChanges = true
                )
            }

            is RecipeIntent.RemoveIngredientClicked -> {
                state.copy(
                    ingredients = state.ingredients.filterNot {
                        it.productPreview.id == intent.productId
                    },
                    hasChanges = true
                )
            }

            RecipeIntent.SaveClicked -> {
                state.copy(
                    isSaving = true,
                    errorMessage = null
                )
            }

            RecipeIntent.DeleteClicked -> {
                state.copy(
                    isDeleting = true,
                    errorMessage = null
                )
            }

            RecipeIntent.RetryClicked -> {
                if (state.recipeId == null) state
                else state.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            else -> state
        }
    }

    fun onLoadSuccess(
        state: RecipeState,
        recipe: RecipeModel
    ): RecipeState {
        return state.copy(
            recipeId = recipe.id,
            createdAt = recipe.createdAt,
            isNewRecipe = false,
            isLoading = false,
            isSaving = false,
            isDeleting = false,
            nameInput = recipe.name,
            servingsYieldInput = recipe.servingsYield.trimSmart(),
            ingredients = recipe.ingredients.map { it.toEditableUi() },
            errorMessage = null,
            hasChanges = false
        )
    }

    fun onSaveSuccess(
        state: RecipeState,
        recipeId: Long
    ): RecipeState {
        return state.copy(
            recipeId = recipeId,
            isNewRecipe = false,
            isSaving = false,
            errorMessage = null,
            hasChanges = false
        )
    }

    fun onDeleteSuccess(
        state: RecipeState
    ): RecipeState {
        return state.copy(
            isDeleting = false,
            errorMessage = null
        )
    }

    fun onError(
        state: RecipeState,
        message: String
    ): RecipeState {
        return state.copy(
            isLoading = false,
            isSaving = false,
            isDeleting = false,
            errorMessage = message
        )
    }

    fun withSummary(
        state: RecipeState,
        summary: RecipeNutritionSummaryUiModel
    ): RecipeState {
        return state.copy(summary = summary)
    }

    fun onDraftLoaded(
        state: RecipeState,
        draft: RecipeDraft
    ): RecipeState {
        return state.copy(
            recipeId = null,
            isNewRecipe = true,
            isLoading = false,
            isSaving = false,
            isDeleting = false,
            nameInput = draft.name,
            servingsYieldInput = draft.servingsYield.toString(),
            ingredients = draft.ingredients.map {
                EditableRecipeIngredientUiModel(
                    productPreview = it.product,
                    amountGramsInput = it.amountGrams.trimSmart()
                )
            },
            errorMessage = null,
            hasChanges = true
        )
    }
}