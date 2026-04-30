package com.saliery.nutrilog.app.presentation.splash

sealed interface SplashEffect {
    object NavigateToHome : SplashEffect
    object NavigateToOnboarding : SplashEffect
}