package com.saliery.nutrilog.app.presentation.recipe

import com.saliery.nutrilog.app.domain.model.business.CookingMethod
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite

data class EditableRecipeIngredientUiModel(
    val productPreview: FoodProductModelLite,
    val amountGramsInput: String,
    val cookingMethod: CookingMethod = CookingMethod.RAW
)