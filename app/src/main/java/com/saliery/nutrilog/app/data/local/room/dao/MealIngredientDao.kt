package com.saliery.nutrilog.app.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saliery.nutrilog.app.data.local.room.entity.entries.MealItemIngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealIngredientDao {

    @Insert
    suspend fun insertIngredients(ingredients: List<MealItemIngredientEntity>)

    @Query("""
        SELECT * FROM meal_item_ingredient
        WHERE mealItemId IN (:itemIds)
    """)
    suspend fun getIngredientsForItems(
        itemIds: List<Long>
    ): List<MealItemIngredientEntity>

    @Query("""
        SELECT * FROM meal_item_ingredient
        WHERE mealItemId IN (:itemIds)
    """)
    fun observeIngredientsForItems(
        itemIds: List<Long>
    ): Flow<List<MealItemIngredientEntity>>
}