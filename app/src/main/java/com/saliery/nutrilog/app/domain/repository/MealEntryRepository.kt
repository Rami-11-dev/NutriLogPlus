package com.saliery.nutrilog.app.domain.repository

import com.saliery.nutrilog.app.domain.model.entries.MealEntryModel
import com.saliery.nutrilog.app.domain.model.entries.MealEntryPreviewModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface MealEntryRepository {
    suspend fun addEntry(entry: MealEntryModel): Long
    suspend fun deleteEntry(entryId: Long): Int
    suspend fun updateEntry(entry: MealEntryModel): Int
    fun observeEntry(entryId: Long): Flow<MealEntryModel?>
    fun observeEntriesForDate(date: LocalDate): Flow<List<MealEntryPreviewModel>>
    fun observeEntriesByDateRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<MealEntryPreviewModel>>
}