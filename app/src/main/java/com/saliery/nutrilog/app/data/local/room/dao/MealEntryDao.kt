package com.saliery.nutrilog.app.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.saliery.nutrilog.app.data.local.room.entity.entries.MealEntryEntity
import com.saliery.nutrilog.app.data.local.room.entity.entries.MealEntryPreviewProjection
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface MealEntryDao {

    @Insert
    suspend fun insertMealEntry(entry: MealEntryEntity): Long

    @Update
    suspend fun updateMealEntry(entry: MealEntryEntity): Int

    @Query("DELETE FROM meal_entry WHERE id = :entryId")
    suspend fun deleteMealEntry(entryId: Long): Int

    @Query("""
        SELECT
            e.id,
            e.mealType,
            e.dateTime,
        
            SUM(p.kcal * i.amountGrams / 100.0) AS caloriesTotal,
            SUM(p.protein * i.amountGrams / 100.0) AS proteinTotal,
            SUM(p.totalFat * i.amountGrams / 100.0) AS fatTotal,
            SUM(p.carbohydrates * i.amountGrams / 100.0) AS carbsTotal
        
        FROM meal_entry e
        
        JOIN meal_item m ON m.mealEntryId = e.id
        JOIN meal_item_ingredient i ON i.mealItemId = m.id
        JOIN product p ON p.id = i.productId
        
        WHERE date(e.dateTime) = date(:date)
        
        GROUP BY e.id
        
        ORDER BY e.dateTime DESC
    """)
    fun observeEntriesForDate(date: LocalDateTime): Flow<List<MealEntryPreviewProjection>>

    @Query("""
        SELECT
            e.id,
            e.mealType,
            e.dateTime,
        
            SUM(p.kcal * i.amountGrams / 100.0) AS caloriesTotal,
            SUM(p.protein * i.amountGrams / 100.0) AS proteinTotal,
            SUM(p.totalFat * i.amountGrams / 100.0) AS fatTotal,
            SUM(p.carbohydrates * i.amountGrams / 100.0) AS carbsTotal
        
        FROM meal_entry e
        
        JOIN meal_item m ON m.mealEntryId = e.id
        JOIN meal_item_ingredient i ON i.mealItemId = m.id
        JOIN product p ON p.id = i.productId
        
        WHERE dateTime BETWEEN :start AND :end
        GROUP BY e.id
        ORDER BY dateTime DESC
    """)
    fun observeEntriesByRange(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<List<MealEntryPreviewProjection>>

    @Query("""
        SELECT * FROM meal_entry
        WHERE id = :entryId
    """)
    fun observeEntry(entryId: Long): Flow<MealEntryEntity?>
}