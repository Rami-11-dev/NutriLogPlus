package com.saliery.nutrilog.app.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.saliery.nutrilog.app.data.local.room.entity.recipe.RecipeIngredientEntity
import com.saliery.nutrilog.app.data.local.room.entity.recipe.RecipeIngredientWithProductWithImageProjection
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeIngredientDao {

    @Insert
    suspend fun insertIngredients(ingredients: List<RecipeIngredientEntity>)

    @Transaction
    @Query("""
        SELECT
            ri.id,
            ri.recipeId,
            ri.amountGrams,
            ri.cookingMethod,
            p.id as productId,
            p.kcal as energyKCaloriesPer100g,
            p.carbohydrates as carbohydratesPer100g,
            p.totalFat as fatsPer100g,
            p.protein as proteinsPer100g,
            p.productName,
            p.productBrand,
            p.source,
            p.foodCategory as foodGroup,
            p.nutritionGrade,
            pi.thumbUrl as displayImage
        FROM recipe_ingredient ri
        LEFT JOIN product p ON ri.productId = p.id
        LEFT JOIN product_image pi ON pi.productId = p.id
            AND pi.imageType = 'FRONT'
        WHERE recipeId = :recipeId
    """)
    fun getIngredientsFlow(
        recipeId: Long
    ): Flow<List<RecipeIngredientWithProductWithImageProjection>>

    @Transaction
    @Query("""
        SELECT
            ri.id,
            ri.recipeId,
            ri.amountGrams,
            ri.cookingMethod,
            p.id as productId,
            p.kcal as energyKCaloriesPer100g,
            p.carbohydrates as carbohydratesPer100g,
            p.totalFat as fatsPer100g,
            p.protein as proteinsPer100g,
            p.productName,
            p.productBrand,
            p.source,
            p.foodCategory as foodGroup,
            p.nutritionGrade,
            pi.thumbUrl as displayImage
        FROM recipe_ingredient ri
        LEFT JOIN product p ON ri.productId = p.id
        LEFT JOIN product_image pi ON pi.productId = p.id
            AND pi.imageType = 'FRONT'
        WHERE recipeId = :recipeId
    """)
    suspend fun getIngredients(
        recipeId: Long
    ): List<RecipeIngredientWithProductWithImageProjection>
}