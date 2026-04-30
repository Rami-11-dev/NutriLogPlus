package com.saliery.nutrilog.app.presentation.helper

import com.saliery.nutrilog.app.domain.model.business.CookingMethod
import com.saliery.nutrilog.app.presentation.recipe.EditableRecipeIngredientUiModel

fun previewEditableRecipeIngredientUiModel(): List<EditableRecipeIngredientUiModel> {

    return listOf(
        EditableRecipeIngredientUiModel(
            productPreview = previewFoodProductModelLiteList()[0],
            amountGramsInput = "400",
            cookingMethod = CookingMethod.RAW,
        ),
        EditableRecipeIngredientUiModel(
            productPreview = previewFoodProductModelLiteList()[1],
            amountGramsInput = "200",
            cookingMethod = CookingMethod.STEAMED
        ),
        EditableRecipeIngredientUiModel(
            productPreview = previewFoodProductModelLiteList()[2],
            amountGramsInput = "250",
            cookingMethod = CookingMethod.FRIED
        )
    )
}