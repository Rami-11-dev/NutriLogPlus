package com.saliery.nutrilog.app.presentation.recipe.helper

import com.saliery.nutrilog.app.domain.model.business.CookingMethod
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.presentation.recipe.EditableRecipeIngredientUiModel

fun FoodProductModelLite.toEditableIngredientUi(): EditableRecipeIngredientUiModel {
    return EditableRecipeIngredientUiModel(
        productPreview = this,
        amountGramsInput = "100",
        cookingMethod = CookingMethod.RAW
    )
}