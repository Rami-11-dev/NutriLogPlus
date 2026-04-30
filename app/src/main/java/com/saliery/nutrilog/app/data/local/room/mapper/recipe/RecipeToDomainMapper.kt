package com.saliery.nutrilog.app.data.local.room.mapper.recipe

import com.saliery.nutrilog.app.data.local.room.entity.recipe.RecipeEntity
import com.saliery.nutrilog.app.domain.model.recipe.RecipeIngredientModel
import com.saliery.nutrilog.app.domain.model.recipe.RecipeModel

fun RecipeEntity.toDomain(ingredients: List<RecipeIngredientModel>): RecipeModel {

    return RecipeModel(
        id = id,
        name = name,
        ingredients = ingredients,
        servingsYield = servingsYield,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
