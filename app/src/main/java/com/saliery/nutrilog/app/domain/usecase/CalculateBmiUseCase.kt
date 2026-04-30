package com.saliery.nutrilog.app.domain.usecase

import com.saliery.nutrilog.app.domain.helper.calculateAgeFromLocalDate
import com.saliery.nutrilog.app.domain.model.user.User
import kotlin.math.pow

class CalculateBmiUseCase {

    operator fun invoke(user: User): Double {

        val heightMeters = user.heightCm / 100.0

        return (user.weightKg / heightMeters.pow(2.0))
    }
}