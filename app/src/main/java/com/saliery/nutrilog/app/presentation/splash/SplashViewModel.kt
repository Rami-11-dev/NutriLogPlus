package com.saliery.nutrilog.app.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saliery.nutrilog.app.domain.repository.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _effect = MutableSharedFlow<SplashEffect>()
    val effect = _effect.asSharedFlow()

    private var loadingJob: Job? = null

    init {
        checkUserStatus()
    }

    private fun checkUserStatus() {
        loadingJob?.cancel()

        loadingJob = viewModelScope.launch {
            delay(500)
            val user = userRepository.getUser()
            if (user != null) {
                _effect.emit(SplashEffect.NavigateToHome)
            } else {
                _effect.emit(SplashEffect.NavigateToOnboarding)
            }
        }
    }
}