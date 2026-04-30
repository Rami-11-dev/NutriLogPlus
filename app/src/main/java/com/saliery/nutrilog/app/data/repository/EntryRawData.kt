package com.saliery.nutrilog.app.data.repository

import com.saliery.nutrilog.app.data.local.room.entity.entries.MealEntryEntity
import com.saliery.nutrilog.app.data.local.room.entity.entries.MealItemEntity
import com.saliery.nutrilog.app.data.local.room.entity.entries.MealItemIngredientEntity
import com.saliery.nutrilog.app.data.local.room.entity.recipe.RecipeEntity
import com.saliery.nutrilog.app.domain.model.entries.LocalMealType
import java.time.LocalDateTime

data class EntryRawData(
    val entry: MealEntryEntity,
    val items: List<MealItemEntity>,
    val ingredients: List<MealItemIngredientEntity>,
    val recipes: Map<Long, RecipeEntity>
) {
    companion object {
        val Empty = EntryRawData(
            entry = MealEntryEntity(0, LocalMealType.BREAKFAST, LocalDateTime.now()),
            items = emptyList(),
            ingredients = emptyList(),
            recipes = emptyMap()
        )
    }
}
