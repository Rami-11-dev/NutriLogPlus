package com.saliery.nutrilog.app.presentation.recipe.helper

import com.saliery.nutrilog.app.domain.model.business.CookingMethod
import com.saliery.nutrilog.app.domain.model.recipe.RecipeIngredientModel
import com.saliery.nutrilog.app.presentation.helper.trimSmart
import com.saliery.nutrilog.app.presentation.recipe.EditableRecipeIngredientUiModel

fun RecipeIngredientModel.toEditableUi(): EditableRecipeIngredientUiModel {
    return EditableRecipeIngredientUiModel(
        productPreview = productPreview,
        amountGramsInput = amountGrams.trimSmart(),
        cookingMethod = cookingMethod ?: CookingMethod.RAW
    )
}