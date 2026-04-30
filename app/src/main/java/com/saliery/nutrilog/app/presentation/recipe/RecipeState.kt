package com.saliery.nutrilog.app.presentation.recipe

import java.time.LocalDateTime

data class RecipeState(
    val recipeId: Long? = null,
    val createdAt: LocalDateTime? = null,
    val isNewRecipe: Boolean = true,

    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,

    val nameInput: String = "",
    val servingsYieldInput: String = "1",

    val ingredients: List<EditableRecipeIngredientUiModel> = emptyList(),
    val summary: RecipeNutritionSummaryUiModel = RecipeNutritionSummaryUiModel(),

    val errorMessage: String? = null,
    val hasChanges: Boolean = false
)
