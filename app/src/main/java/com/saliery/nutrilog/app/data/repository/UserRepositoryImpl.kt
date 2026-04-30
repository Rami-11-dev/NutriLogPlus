package com.saliery.nutrilog.app.data.repository

import com.saliery.nutrilog.app.data.local.preferences.UserLocalDataSource
import com.saliery.nutrilog.app.domain.model.user.User
import com.saliery.nutrilog.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow


class UserRepositoryImpl(
    private val localDataSource: UserLocalDataSource
) : UserRepository {
    override fun observeUser(): Flow<User?> {
        return localDataSource.observeUser()
    }

    override suspend fun getUser(): User? {
        return localDataSource.getUser()
    }

    override suspend fun saveUser(user: User) {
        localDataSource.saveUser(user)
    }
}