package com.saliery.nutrilog.app.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.saliery.nutrilog.app.data.local.room.entity.user.WeightEntryEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface WeightEntryDao {

    @Insert
    suspend fun insertWeightEntry(entry: WeightEntryEntity): Long

    @Update
    suspend fun updateWeightEntry(entry: WeightEntryEntity): Int

    @Query("DELETE FROM weight_entry WHERE id = :entryId")
    suspend fun deleteWeightEntry(entryId: Long): Int

    @Query("""
        SELECT * FROM weight_entry
        ORDER BY dateTime DESC
    """)
    fun observeWeightEntries(): Flow<List<WeightEntryEntity>>

    @Query("""
        SELECT * FROM weight_entry
        ORDER BY dateTime DESC
        LIMIT 1
    """)
    fun observeLatestWeightEntry(): Flow<WeightEntryEntity?>

    @Query("""
        SELECT * FROM weight_entry
        WHERE dateTime BETWEEN :start AND :end
        ORDER BY dateTime ASC
    """)
    fun observeWeightEntriesByRange(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<List<WeightEntryEntity>>
}