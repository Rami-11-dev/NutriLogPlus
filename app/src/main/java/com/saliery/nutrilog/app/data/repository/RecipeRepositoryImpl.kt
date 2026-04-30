package com.saliery.nutrilog.app.data.repository

import com.saliery.nutrilog.app.data.local.room.dao.RecipeDao
import com.saliery.nutrilog.app.data.local.room.dao.RecipeIngredientDao
import com.saliery.nutrilog.app.data.local.room.mapper.recipe.toDomain
import com.saliery.nutrilog.app.data.local.room.mapper.recipe.toEntity
import com.saliery.nutrilog.app.data.local.room.mapper.recipe.toListItemModel
import com.saliery.nutrilog.app.domain.model.recipe.RecipeListItemModel
import com.saliery.nutrilog.app.domain.model.recipe.RecipeModel
import com.saliery.nutrilog.app.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class RecipeRepositoryImpl(
    private val recipeDao: RecipeDao,
    private val recipeIngredientDao: RecipeIngredientDao,
) : RecipeRepository {

    override suspend fun addRecipe(recipe: RecipeModel): Long {
        return recipeDao.insertRecipeWithIngredients(
            recipe = recipe.toEntity(),
            ingredients = recipe.ingredients.map {
                it.toEntity()
            }
        )
    }

    override suspend fun deleteRecipe(recipeId: Long): Int {
        return recipeDao.deleteRecipe(recipeId)
    }

    override suspend fun updateRecipe(recipe: RecipeModel): Int {
        return recipeDao.updateRecipe(recipe.toEntity())
    }

    override suspend fun getRecipeById(recipeId: Long): RecipeModel? {
        val recipeEntity = recipeDao.getRecipe(recipeId) ?: return null
        val ingredients = recipeIngredientDao.getIngredients(recipeId)

        // todo: Need to add fallback
        return recipeEntity.toDomain(
            ingredients.map { it.toDomain() }
        )
    }

    override fun observeRecipe(recipeId: Long): Flow<RecipeModel?> =
        combine(
            recipeDao.observeRecipeFlow(recipeId),
            recipeIngredientDao.getIngredientsFlow(recipeId)
        ) { recipe, ingredients ->
            recipe.toDomain(
                ingredients = ingredients.map { it.toDomain() }
            )
        }

    override fun searchRecipes(query: String): Flow<List<RecipeListItemModel>> = flow {
        recipeDao.searchRecipeFlow(query)
            .collect { recipeEntities ->
                val result = mutableListOf<RecipeListItemModel>()

                recipeEntities.forEach { recipe ->
                    val ingredients = recipeIngredientDao.getIngredients(recipe.id)

                    val recipeModel = recipe.toDomain(
                        ingredients.map { it.toDomain() }
                    )

                    result.add(recipeModel.toListItemModel())
                }

                emit(result)
            }
    }

    override fun getRecipes(): Flow<List<RecipeListItemModel>> = flow {
        recipeDao.getRecipesFlow()
            .collect { recipeEntities ->
                val result = mutableListOf<RecipeListItemModel>()

                recipeEntities.forEach { recipe ->
                    val ingredients = recipeIngredientDao.getIngredients(recipe.id)

                    val recipeModel = recipe.toDomain(
                        ingredients.map { it.toDomain() }
                    )

                    result.add(recipeModel.toListItemModel())
                }

                emit(result)
            }
    }

    override fun getRecipeCount(): Flow<Int> =
        recipeDao.getRecipeCountFlow()
}