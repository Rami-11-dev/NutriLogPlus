package com.saliery.nutrilog.app.data.local.room.mapper.recipe

import com.saliery.nutrilog.app.data.local.room.entity.recipe.RecipeEntity
import com.saliery.nutrilog.app.domain.model.recipe.RecipeModel


fun RecipeModel.toEntity(): RecipeEntity {

    return RecipeEntity(
        name = name,
        servingsYield = servingsYield,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}