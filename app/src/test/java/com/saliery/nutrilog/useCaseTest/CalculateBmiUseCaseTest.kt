package com.saliery.nutrilog.useCaseTest

import com.saliery.nutrilog.app.domain.usecase.CalculateBmiUseCase
import com.saliery.nutrilog.app.presentation.helper.previewUser
import org.junit.Test
import kotlin.test.assertEquals

class CalculateBmiUseCaseTest {

    private val calculateBmiUseCaseTest = CalculateBmiUseCase()

    @Test
    fun shouldCorrectlyCalculateBmi() {
        val user = previewUser()

        val result = calculateBmiUseCaseTest(user)

        assertEquals(18.51, result, 0.01)
    }
}