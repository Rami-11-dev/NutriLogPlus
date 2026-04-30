package com.saliery.nutrilog.app.domain.repository

import com.saliery.nutrilog.app.domain.model.recipe.RecipeListItemModel
import com.saliery.nutrilog.app.domain.model.recipe.RecipeModel
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun addRecipe(recipe: RecipeModel): Long
    suspend fun deleteRecipe(recipeId: Long): Int
    suspend fun updateRecipe(recipe: RecipeModel): Int
    suspend fun getRecipeById(recipeId: Long): RecipeModel?

    fun observeRecipe(recipeId: Long): Flow<RecipeModel?>
    fun searchRecipes(query: String): Flow<List<RecipeListItemModel>>
    fun getRecipes(): Flow<List<RecipeListItemModel>>
    fun getRecipeCount(): Flow<Int>
}