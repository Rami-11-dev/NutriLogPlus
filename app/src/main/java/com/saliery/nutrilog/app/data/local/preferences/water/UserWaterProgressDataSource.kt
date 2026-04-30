package com.saliery.nutrilog.app.data.local.preferences.water

import com.saliery.nutrilog.app.domain.model.user.DailyWaterProgress
import kotlinx.coroutines.flow.Flow

interface UserWaterProgressDataSource {
    fun observeTodayProgress(): Flow<DailyWaterProgress>
    suspend fun addWater(amountMl: Double)
    suspend fun resetTodayProgress()
}