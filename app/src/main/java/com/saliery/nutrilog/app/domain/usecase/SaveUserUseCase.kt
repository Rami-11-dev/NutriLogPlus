package com.saliery.nutrilog.app.domain.usecase

import com.saliery.nutrilog.app.domain.model.user.User
import com.saliery.nutrilog.app.domain.repository.UserRepository

class SaveUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        userRepository.saveUser(user = user)
    }
}