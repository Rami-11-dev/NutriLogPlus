package com.saliery.nutrilog.app.domain.usecase

import com.saliery.nutrilog.app.domain.model.user.WeightEntryModel
import com.saliery.nutrilog.app.domain.repository.UserRepository
import com.saliery.nutrilog.app.domain.repository.WeightRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime

class SaveWeightUseCase(
    private val userRepository: UserRepository,
    private val weightRepository: WeightRepository
) {
    suspend operator fun invoke(weightKg: Double, dateTime: LocalDateTime) {
        weightRepository.addWeightEntry(
            WeightEntryModel(
                id = 0L,
                weightKg = weightKg,
                dateTime = dateTime
            )
        )

        val currentUser = userRepository.getUser() ?: return

        userRepository.saveUser(
            currentUser.copy(weightKg = weightKg)
        )
    }
}