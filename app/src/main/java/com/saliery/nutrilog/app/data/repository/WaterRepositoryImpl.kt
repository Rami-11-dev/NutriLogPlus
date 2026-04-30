package com.saliery.nutrilog.app.data.repository

import com.saliery.nutrilog.app.data.local.preferences.water.UserWaterProgressDataSource
import com.saliery.nutrilog.app.domain.model.user.DailyWaterProgress
import com.saliery.nutrilog.app.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow

class WaterRepositoryImpl(
    private val dataSource: UserWaterProgressDataSource
) : WaterRepository {
    override fun observeTodayProgress(): Flow<DailyWaterProgress> {
        return dataSource.observeTodayProgress()
    }

    override suspend fun addWater(amountMl: Double) {
        dataSource.addWater(amountMl)
    }

    override suspend fun resetTodayProgress() {
        dataSource.resetTodayProgress()
    }
}