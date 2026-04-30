package com.saliery.nutrilog.useCaseTest

import com.saliery.nutrilog.app.domain.usecase.CalculateDailyWaterUseCase
import com.saliery.nutrilog.app.presentation.helper.previewUser
import org.junit.Test
import kotlin.test.assertEquals

class CalculateDailyWaterUseCaseTest {

    private val calculateDailyWaterUseCaseTest = CalculateDailyWaterUseCase()

    @Test
    fun shouldCorrectlyReturnDailyWaterRecommendation() {
        val user = previewUser()

        val result = calculateDailyWaterUseCaseTest(user)

        assertEquals(2100.0, result.baseAmount, 0.1)
        assertEquals(1.0, result.sexAdjustment, 0.1)
        assertEquals(1.0, result.ageAdjustment, 0.1)
        assertEquals(1.0, result.goalAdjustment, 0.1)
        assertEquals(1.25, result.activityAdjustment, 0.01)
        assertEquals(1.0, result.goalAdjustment, 0.1)
        assertEquals(2625.0, result.waterMl, 0.1)
        assertEquals(2.6, result.waterLiters, 0.1)
    }
}