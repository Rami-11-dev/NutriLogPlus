package com.saliery.nutrilog.app.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.saliery.nutrilog.app.data.local.room.entity.entries.MealItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealItemDao {

    @Insert
    suspend fun insertItems(items: List<MealItemEntity>): List<Long>

    @Update
    suspend fun updateItems(items: List<MealItemEntity>): Int

    @Query(
        """
            DELETE FROM meal_item
            WHERE mealEntryId = :entryId
        """)
    suspend fun deleteItemsByEntryId(entryId: Long): Int

    @Query("""
        SELECT * FROM meal_item
        WHERE mealEntryId = :entryId
    """)
    suspend fun getItemsForEntry(entryId: Long): List<MealItemEntity>

    @Query("""
        SELECT * FROM meal_item
        WHERE mealEntryId = :entryId
    """)
    fun observeItemsForEntry(entryId: Long): Flow<List<MealItemEntity>>
}