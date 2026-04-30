package com.saliery.nutrilog.app.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashRoute(
    onNavigateToHome: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
    viewModel: SplashViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SplashEffect.NavigateToHome -> onNavigateToHome()
                SplashEffect.NavigateToOnboarding -> onNavigateToOnboarding()
            }
        }
    }

    SplashScreen()
}