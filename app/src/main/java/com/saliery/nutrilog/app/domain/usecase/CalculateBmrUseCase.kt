package com.saliery.nutrilog.app.domain.usecase

import com.saliery.nutrilog.app.domain.helper.calculateAgeFromLocalDate
import com.saliery.nutrilog.app.domain.model.user.Sex
import com.saliery.nutrilog.app.domain.model.user.User

// Mifflin-Saint Geor's BMR formula
class CalculateBmrUseCase {
    operator fun invoke(user: User): Double {
        val userAge = calculateAgeFromLocalDate(user.birthDate)

        return when (user.sex) {
            Sex.MALE ->
                10 * user.weightKg + 6.25 * user.heightCm - 5 * userAge + 5
            Sex.FEMALE ->
                10 * user.weightKg + 6.25 * user.heightCm - 5 * userAge - 161
        }
    }
}