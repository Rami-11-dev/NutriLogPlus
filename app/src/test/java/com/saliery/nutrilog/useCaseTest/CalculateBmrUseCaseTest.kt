package com.saliery.nutrilog.useCaseTest

import com.saliery.nutrilog.app.domain.model.user.ActivityLevel
import com.saliery.nutrilog.app.domain.model.user.Goal
import com.saliery.nutrilog.app.domain.model.user.Sex
import com.saliery.nutrilog.app.domain.model.user.User
import com.saliery.nutrilog.app.domain.usecase.CalculateBmrUseCase
import junit.framework.TestCase
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DateTimeFormat
import org.junit.Test

class CalculateBmrUseCaseTest {

    private val useCase = CalculateBmrUseCase()

    @Test
    fun shouldCalculateCorrectBMRforMale() {
        val user = User(
            weightKg = 70.0,
            heightCm = 175.0,
            birthDate = LocalDate.parse("2003-12-23"),
            sex = Sex.MALE,
            activityLevel = ActivityLevel.MODERATE,
            goal = Goal.MAINTAIN
        )

        val result = useCase(user)

        val expected = 10 * 70 + 6.25 * 175 - 5 * 22 + 5

        TestCase.assertEquals(expected, result, 0.01)
    }
}