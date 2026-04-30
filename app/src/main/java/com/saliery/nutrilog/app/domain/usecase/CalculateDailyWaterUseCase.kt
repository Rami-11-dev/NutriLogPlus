package com.saliery.nutrilog.app.domain.usecase

import com.saliery.nutrilog.app.domain.helper.calculateAgeFromLocalDate
import com.saliery.nutrilog.app.domain.model.business.DailyWaterRecommendation
import com.saliery.nutrilog.app.domain.model.user.Goal
import com.saliery.nutrilog.app.domain.model.user.Sex
import com.saliery.nutrilog.app.domain.model.user.User

class CalculateDailyWaterUseCase {

    operator fun invoke(user: User): DailyWaterRecommendation {

        val age = calculateAgeFromLocalDate(user.birthDate)

        val baseWaterMl = user.weightKg * 35.0

        val sexAdjustment = when (user.sex) {
            Sex.MALE -> 1.0
            Sex.FEMALE -> 0.95
        }

        val ageAdjustment = when {
            age < 18 -> 0.9
            age in 18..65 -> 1.0
            else -> 1.1
        }

        val goalAdjustment = when (user.goal) {
            Goal.LOSE_WEIGHT -> 1.1
            Goal.MAINTAIN -> 1.0
            Goal.GAIN_WEIGHT -> 0.95
        }

        val activityAdjustment = user.activityLevel.waterMultiplier

        val totalWaterMl = baseWaterMl * sexAdjustment * ageAdjustment *
                activityAdjustment * goalAdjustment

        val clampedWaterMl = totalWaterMl.coerceIn(1500.0, 4000.0)
        val waterLiters = ((clampedWaterMl  / 1000) * 10).toInt() / 10.0

        return DailyWaterRecommendation(
            waterLiters = waterLiters,
            waterMl = clampedWaterMl,
            baseAmount = baseWaterMl,
            activityAdjustment = activityAdjustment,
            goalAdjustment = goalAdjustment,
            ageAdjustment = ageAdjustment,
            sexAdjustment = sexAdjustment
        )
    }
}