package com.saliery.nutrilog.app.data.local.preferences

import com.saliery.nutrilog.app.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun observeUser(): Flow<User?>
    suspend fun getUser(): User?
    suspend fun saveUser(user: User)
}