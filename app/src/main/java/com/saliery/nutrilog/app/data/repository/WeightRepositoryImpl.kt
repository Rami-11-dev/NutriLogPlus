package com.saliery.nutrilog.app.data.repository

import com.saliery.nutrilog.app.data.local.room.dao.WeightEntryDao
import com.saliery.nutrilog.app.data.local.room.mapper.weightEntry.toDomain
import com.saliery.nutrilog.app.data.local.room.mapper.weightEntry.toEntity
import com.saliery.nutrilog.app.domain.model.user.WeightEntryModel
import com.saliery.nutrilog.app.domain.repository.WeightRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class WeightRepositoryImpl(
    private val weightEntryDao: WeightEntryDao
) : WeightRepository {

    override suspend fun addWeightEntry(entry: WeightEntryModel): Long {
        return weightEntryDao.insertWeightEntry(entry.toEntity())
    }

    override suspend fun updateWeightEntry(entry: WeightEntryModel): Int {
        return weightEntryDao.updateWeightEntry(entry.toEntity())
    }

    override suspend fun deleteWeightEntry(entryId: Long): Int {
        return weightEntryDao.deleteWeightEntry(entryId)
    }

    override fun observeWeightEntries(): Flow<List<WeightEntryModel>> {
        return weightEntryDao.observeWeightEntries()
            .map { list -> list.map { it.toDomain() } }
    }

    override fun observeLatestWeightEntry(): Flow<WeightEntryModel?> {
        return weightEntryDao.observeLatestWeightEntry()
            .map { it?.toDomain() }
    }

    override fun observeWeightEntriesByRange(
        start: LocalDate,
        end: LocalDate
    ): Flow<List<WeightEntryModel>> {
        return weightEntryDao.observeWeightEntriesByRange(
            start = start.atStartOfDay(),
            end = end.atTime(23, 59, 59)
        ).map { list -> list.map { it.toDomain() } }
    }
}