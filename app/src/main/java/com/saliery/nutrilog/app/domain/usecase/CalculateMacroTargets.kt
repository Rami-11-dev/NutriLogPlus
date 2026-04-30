package com.saliery.nutrilog.app.domain.usecase

import com.saliery.nutrilog.app.domain.model.user.Goal
import com.saliery.nutrilog.app.domain.model.business.MacroTargets

class CalculateMacroTargets {

    operator fun invoke(
        dailyCalories: Double,
        goal: Goal
    ): MacroTargets {

        val (proteinRatio, fatRatio, carbRatio) = when (goal) {
            Goal.LOSE_WEIGHT -> Triple(0.30, 0.25, 0.45)
            Goal.MAINTAIN -> Triple(0.25, 0.30, 0.45)
            Goal.GAIN_WEIGHT -> Triple(0.25, 0.25, 0.50)
        }

        val proteinCalories = dailyCalories * proteinRatio
        val fatCalories = dailyCalories * fatRatio
        val carbCalories = dailyCalories * carbRatio

        return MacroTargets(
            proteinGrams = proteinCalories / 4,  // 1g protein = 4 kcal
            fatGrams = fatCalories / 9,          // 1g fat = 9 kcal
            carbGrams = carbCalories / 4         // 1g carb = 4 kcal
        )
    }
}