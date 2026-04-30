package com.saliery.nutrilog.app.domain.repository

import com.saliery.nutrilog.app.domain.model.user.WeightEntryModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface WeightRepository {

    suspend fun addWeightEntry(entry: WeightEntryModel): Long
    suspend fun updateWeightEntry(entry: WeightEntryModel): Int
    suspend fun deleteWeightEntry(entryId: Long): Int

    fun observeWeightEntries(): Flow<List<WeightEntryModel>>
    fun observeLatestWeightEntry(): Flow<WeightEntryModel?>
    fun observeWeightEntriesByRange(
        start: LocalDate,
        end: LocalDate
    ): Flow<List<WeightEntryModel>>
}