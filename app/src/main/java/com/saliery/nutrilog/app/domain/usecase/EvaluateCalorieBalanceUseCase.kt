package com.saliery.nutrilog.app.domain.usecase

import com.saliery.nutrilog.app.domain.model.business.CalorieBalance

class EvaluateCalorieBalanceUseCase {

    operator fun invoke(
        consumed: Double,
        target: Double
    ): CalorieBalance {

        val diff = consumed - target

        return CalorieBalance(
            difference = diff,
            isDeficit = diff < 0,
            isSurplus = diff > 0
        )
    }
}