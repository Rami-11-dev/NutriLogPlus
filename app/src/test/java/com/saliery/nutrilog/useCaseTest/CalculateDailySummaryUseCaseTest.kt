package com.saliery.nutrilog.useCaseTest

import com.saliery.nutrilog.app.domain.usecase.CalculateDailySummaryUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateMealItemNutritionUseCase
import com.saliery.nutrilog.app.presentation.helper.previewMealEntryPreviewModel
import org.junit.Test
import kotlin.test.assertEquals

class CalculateDailySummaryUseCaseTest {

    private val useCase = CalculateDailySummaryUseCase(
        calculateMealItemNutrition = CalculateMealItemNutritionUseCase()
    )

    @Test
    fun shouldCorrectlyCalculateTotals() {

        val product = previewMealEntryPreviewModel()

        val result = useCase(product)

        assertEquals(2200.0, result.totalCalories, 0.01)
        assertEquals(108.0, result.totalProteins, 0.01)
        assertEquals(75.0, result.totalFats, 0.01)
        assertEquals(262.0, result.totalCarbs, 0.01)
    }
}