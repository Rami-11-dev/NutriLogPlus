package com.saliery.nutrilog.app.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.saliery.nutrilog.app.data.local.room.entity.recipe.RecipeEntity
import com.saliery.nutrilog.app.data.local.room.entity.recipe.RecipeIngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity): Long

    @Insert
    suspend fun insertIngredients(ingredients: List<RecipeIngredientEntity>)

    @Transaction
    suspend fun insertRecipeWithIngredients(
        recipe: RecipeEntity, ingredients: List<RecipeIngredientEntity>
    ): Long {

        val recipeId = insertRecipe(recipe)

        val ingredientsWithRecipeId = ingredients.map {
            it.copy(recipeId = recipeId)
        }

        insertIngredients(ingredientsWithRecipeId)
        return recipeId
    }

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity): Int

    @Query("DELETE FROM recipe WHERE id = :recipeId")
    suspend fun deleteRecipe(recipeId: Long): Int

    @Query("SELECT * FROM recipe ORDER BY updatedAt DESC")
    fun getRecipesFlow(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun observeRecipeFlow(id: Long): Flow<RecipeEntity>

    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun getRecipe(id: Long): RecipeEntity

    @Query("SELECT COUNT(*) FROM recipe")
    fun getRecipeCountFlow(): Flow<Int>

    @Query("SELECT * FROM recipe WHERE name LIKE '%' || :query || '%'")
    fun searchRecipeFlow(query: String): Flow<List<RecipeEntity>>
}