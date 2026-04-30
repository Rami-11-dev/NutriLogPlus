package com.saliery.nutrilog.app.domain.model.recipe

import com.saliery.nutrilog.app.domain.model.business.CookingMethod
import com.saliery.nutrilog.app.domain.model.product.FoodProductModel
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite

data class RecipeIngredientModel(
    val productPreview: FoodProductModelLite,
    val cookingMethod: CookingMethod? = CookingMethod.RAW,
    // The whole recipe amount
    val amountGrams: Double
)
