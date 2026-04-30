package com.saliery.nutrilog.app.domain.usecase

import com.saliery.nutrilog.app.domain.model.user.Goal
import com.saliery.nutrilog.app.domain.model.user.User

class CalculateDailyCaloriesUseCase(
    private val calculateBmr: CalculateBmrUseCase
) {
    operator fun invoke(user: User): Double {
        val bmr = calculateBmr(user)
        val tdee = bmr * user.activityLevel.multiplier

        return when (user.goal) {
            Goal.LOSE_WEIGHT -> tdee * 0.85
            Goal.MAINTAIN -> tdee
            Goal.GAIN_WEIGHT -> tdee * 1.1
        }
    }
}