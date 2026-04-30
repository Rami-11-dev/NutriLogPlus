package com.saliery.nutrilog.repositoryTest

import com.saliery.nutrilog.app.data.local.preferences.UserLocalDataSource
import com.saliery.nutrilog.app.data.repository.UserRepositoryImpl
import com.saliery.nutrilog.app.domain.model.user.ActivityLevel
import com.saliery.nutrilog.app.domain.model.user.Goal
import com.saliery.nutrilog.app.domain.model.user.Sex
import com.saliery.nutrilog.app.presentation.helper.previewUser
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertNotNull
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class UserRepositoryTest {

    val user = previewUser()

    @Mock
    private lateinit var localDataSource: UserLocalDataSource
    private lateinit var userRepository: UserRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        userRepository = UserRepositoryImpl(
            localDataSource = localDataSource
        )
    }

    @Test
    fun testSaveUser(): Unit = runBlocking {
        userRepository.saveUser(user)
        verify(localDataSource).saveUser(user)
    }

    @Test
    fun testGetUser(): Unit = runBlocking {

        whenever(localDataSource.observeUser())
            .thenReturn(flowOf(user))

        val retrievedUser = userRepository.observeUser()

        retrievedUser.collect { user ->
            assertNotNull(user)
            println("RETRIEVED USER: $user")
            assertEquals(60.0, user.weightKg)
            assertEquals(180.0, user.heightCm)
            assertEquals(LocalDate.parse("2000-12-20"), user.birthDate)
            assertEquals(Sex.MALE, user.sex)
            assertEquals(ActivityLevel.MODERATE, user.activityLevel)
            assertEquals(Goal.MAINTAIN, user.goal)
        }

        verify(localDataSource).observeUser()
    }

    @Test
    fun testGetUserReturnNull() = runTest {
        whenever(localDataSource.observeUser())
            .thenReturn(flowOf(null))

        val retrievedUser = userRepository.observeUser()

        retrievedUser.collect { user ->
            assertEquals(null, user)
        }
    }
}