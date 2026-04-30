package com.saliery.nutrilog.app.data.repository

import androidx.room.withTransaction
import com.saliery.nutrilog.app.data.local.room.AppDatabase
import com.saliery.nutrilog.app.data.local.room.dao.MealEntryDao
import com.saliery.nutrilog.app.data.local.room.dao.MealIngredientDao
import com.saliery.nutrilog.app.data.local.room.dao.MealItemDao
import com.saliery.nutrilog.app.data.local.room.dao.ProductDao
import com.saliery.nutrilog.app.data.local.room.dao.RecipeDao
import com.saliery.nutrilog.app.data.local.room.dao.RecipeIngredientDao
import com.saliery.nutrilog.app.data.local.room.entity.entries.MealEntryEntity
import com.saliery.nutrilog.app.data.local.room.entity.entries.MealItemEntity
import com.saliery.nutrilog.app.data.local.room.entity.entries.MealItemIngredientEntity
import com.saliery.nutrilog.app.data.local.room.mapper.mealentry.toDomain
import com.saliery.nutrilog.app.data.local.room.mapper.product.toDomain
import com.saliery.nutrilog.app.data.local.room.mapper.recipe.toDomain
import com.saliery.nutrilog.app.domain.model.entries.LocalMealItemType
import com.saliery.nutrilog.app.domain.model.entries.MealEntryModel
import com.saliery.nutrilog.app.domain.model.entries.MealEntryPreviewModel
import com.saliery.nutrilog.app.domain.model.entries.MealItemModel
import com.saliery.nutrilog.app.domain.repository.MealEntryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import java.time.LocalDate

class MealEntryRepositoryImpl(
    private val mealEntryDao: MealEntryDao,
    private val mealIngredientDao: MealIngredientDao,
    private val mealItemDao: MealItemDao,
    private val productDao: ProductDao,
    private val recipeDao: RecipeDao,
    private val recipeIngredientDao: RecipeIngredientDao,
    private val db: AppDatabase
) : MealEntryRepository {

    override suspend fun addEntry(entry: MealEntryModel): Long = db.withTransaction {
        val entryId = mealEntryDao.insertMealEntry(
            MealEntryEntity(
                mealType = entry.mealType,
                dateTime = entry.dateTime
            )
        )

        val itemEntities = entry.items.map { item ->
            item.toMealItemEntity(mealEntryId = entryId)
        }

        val itemIds = mealItemDao.insertItems(itemEntities)

        val ingredientEntities = buildSnapshotIngredients(
            items = entry.items,
            itemIds = itemIds
        )

        mealIngredientDao.insertIngredients(ingredientEntities)

        entryId
    }

    override suspend fun deleteEntry(entryId: Long): Int {
        return mealEntryDao.deleteMealEntry(entryId)
    }

    override suspend fun updateEntry(entry: MealEntryModel): Int = db.withTransaction {
        var updatedCount = 0

        updatedCount += mealEntryDao.updateMealEntry(
            MealEntryEntity(
                id = entry.id,
                mealType = entry.mealType,
                dateTime = entry.dateTime
            )
        )

        mealItemDao.deleteItemsByEntryId(entry.id)

        val itemEntities = entry.items.map { item ->
            item.toMealItemEntity(mealEntryId = entry.id)
        }

        val insertedItemIds = mealItemDao.insertItems(itemEntities)

        val ingredientEntities = buildSnapshotIngredients(
            items = entry.items,
            itemIds = insertedItemIds
        )

        mealIngredientDao.insertIngredients(ingredientEntities)

        updatedCount
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeEntry(entryId: Long): Flow<MealEntryModel?> {
        return mealEntryDao.observeEntry(entryId)
            .mapLatest { entity ->
                entity ?: return@mapLatest null
                val rawData = loadEntryData(entryId)
                rawData.toDomain()
            }
    }

    private suspend fun loadEntryData(entryId: Long): EntryRawData {
        val entry = mealEntryDao.observeEntry(entryId).first() ?: return EntryRawData.Empty

        val items = mealItemDao.getItemsForEntry(entryId)
        val itemIds = items.map { it.id }

        val ingredients = if (itemIds.isNotEmpty()) {
            mealIngredientDao.getIngredientsForItems(itemIds)
        } else {
            emptyList()
        }

        val recipeIds = items.mapNotNull { it.recipeId }.distinct()

        val recipes = if (recipeIds.isNotEmpty()) {
            recipeIds.associateWith { recipeDao.getRecipe(it) }
        } else {
            emptyMap()
        }

        return EntryRawData(
            entry = entry,
            items = items,
            ingredients = ingredients,
            recipes = recipes
        )
    }

    private suspend fun EntryRawData.toDomain(): MealEntryModel {
        val ingredientsByItem = ingredients.groupBy { it.mealItemId }

        return MealEntryModel(
            id = entry.id,
            mealType = entry.mealType,
            dateTime = entry.dateTime,
            items = items.mapNotNull { item ->
                val itemIngredients = ingredientsByItem[item.id].orEmpty()

                when (item.itemType) {
                    LocalMealItemType.RECIPE -> {
                        val recipeId = item.recipeId ?: return@mapNotNull null
                        val recipeEntity = recipes[recipeId] ?: return@mapNotNull null

                        val recipeIngredients = recipeIngredientDao.getIngredients(recipeEntity.id)

                        MealItemModel.RecipeItemModel(
                            id = item.id,
                            cookingMethod = item.cookingMethod,
                            consumedServings = item.consumedServings ?: 1.0,
                            recipeModel = recipeEntity.toDomain(
                                recipeIngredients.map { it.toDomain() }
                            )
                        )
                    }

                    LocalMealItemType.PRODUCT -> {
                        val ingredient = itemIngredients.firstOrNull() ?: return@mapNotNull null

                        val product = productDao
                            .getProductEntitiesLite(listOf(ingredient.productId))
                            .firstOrNull()
                            ?: return@mapNotNull null

                        MealItemModel.ProductItemModel(
                            id = item.id,
                            cookingMethod = item.cookingMethod,
                            consumedGrams = item.consumedGrams ?: ingredient.amountGrams,
                            product = product.toDomain()
                        )
                    }
                }
            }
        )
    }

    override fun observeEntriesForDate(date: LocalDate): Flow<List<MealEntryPreviewModel>> {
        return mealEntryDao.observeEntriesForDate(date.atStartOfDay())
            .map { projections -> projections.map { it.toDomain() } }
    }

    override fun observeEntriesByDateRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<MealEntryPreviewModel>> {
        return mealEntryDao.observeEntriesByRange(
            start = startDate.atStartOfDay(),
            end = endDate.atTime(23, 59, 59, 999_999_999)
        ).map { projections ->
            projections.map { it.toDomain() }
        }
    }

    private fun MealItemModel.toMealItemEntity(mealEntryId: Long): MealItemEntity {
        return when (this) {
            is MealItemModel.ProductItemModel -> {
                MealItemEntity(
                    mealEntryId = mealEntryId,
                    recipeId = null,
                    itemType = LocalMealItemType.PRODUCT,
                    cookingMethod = cookingMethod,
                    consumedGrams = consumedGrams,
                    consumedServings = null
                )
            }

            is MealItemModel.RecipeItemModel -> {
                MealItemEntity(
                    mealEntryId = mealEntryId,
                    recipeId = recipeModel.id,
                    itemType = LocalMealItemType.RECIPE,
                    cookingMethod = cookingMethod,
                    consumedGrams = null,
                    consumedServings = consumedServings
                )
            }
        }
    }

    private fun buildSnapshotIngredients(
        items: List<MealItemModel>,
        itemIds: List<Long>
    ): List<MealItemIngredientEntity> {
        return buildList {
            items.zip(itemIds).forEach { (item, itemId) ->
                when (item) {
                    is MealItemModel.ProductItemModel -> {
                        add(
                            MealItemIngredientEntity(
                                mealItemId = itemId,
                                productId = item.product.id,
                                amountGrams = item.consumedGrams
                            )
                        )
                    }

                    is MealItemModel.RecipeItemModel -> {
                        val servingsYield = item.recipeModel.servingsYield
                        require(servingsYield > 0.0) {
                            "Recipe servingsYield must be greater than 0"
                        }

                        item.recipeModel.ingredients.forEach { ingredient ->
                            val gramsPerServing = ingredient.amountGrams / servingsYield
                            val consumedIngredientGrams = gramsPerServing * item.consumedServings

                            add(
                                MealItemIngredientEntity(
                                    mealItemId = itemId,
                                    productId = ingredient.productPreview.id,
                                    amountGrams = consumedIngredientGrams
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}