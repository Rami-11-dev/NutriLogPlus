package com.saliery.nutrilog.app.domain.repository

import com.saliery.nutrilog.app.domain.model.user.DailyWaterProgress
import kotlinx.coroutines.flow.Flow

interface WaterRepository {
    fun observeTodayProgress(): Flow<DailyWaterProgress>
    suspend fun addWater(amountMl: Double)
    suspend fun resetTodayProgress()
}