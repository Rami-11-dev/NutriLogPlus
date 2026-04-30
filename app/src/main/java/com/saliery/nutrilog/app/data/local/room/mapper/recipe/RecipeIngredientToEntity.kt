package com.saliery.nutrilog.app.data.local.room.mapper.recipe

import com.saliery.nutrilog.app.data.local.room.entity.recipe.RecipeIngredientEntity
import com.saliery.nutrilog.app.domain.model.recipe.RecipeIngredientModel

fun RecipeIngredientModel.toEntity(): RecipeIngredientEntity {

    return RecipeIngredientEntity(
        recipeId = 0L,
        productId = productPreview.id,
        cookingMethod = cookingMethod,
        amountGrams = amountGrams
    )
}