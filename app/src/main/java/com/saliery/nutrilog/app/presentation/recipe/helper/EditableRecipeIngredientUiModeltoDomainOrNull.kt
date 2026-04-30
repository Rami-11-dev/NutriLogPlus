package com.saliery.nutrilog.app.presentation.recipe.helper

import com.saliery.nutrilog.app.domain.model.recipe.RecipeIngredientModel
import com.saliery.nutrilog.app.presentation.recipe.EditableRecipeIngredientUiModel

fun EditableRecipeIngredientUiModel.toDomainOrNull(): RecipeIngredientModel? {
    val grams = amountGramsInput
        .replace(',', '.')
        .toDoubleOrNull()
        ?.takeIf { it > 0.0 }
        ?: return null

    return RecipeIngredientModel(
        productPreview = productPreview,
        cookingMethod = cookingMethod,
        amountGrams = grams
    )
}