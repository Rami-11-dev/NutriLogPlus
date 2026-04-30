package com.saliery.nutrilog.app.domain.repository

import com.saliery.nutrilog.app.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeUser(): Flow<User?>
    suspend fun getUser(): User?
    suspend fun saveUser(user: User)
}