package com.saliery.nutrilog.app.presentation.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnboardingRoute(
    viewModel: OnboardingViewModel = koinViewModel(),
    onNavigateToHome: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isCompleted) {
        if (state.isCompleted) {
            onNavigateToHome()
        }
    }

    OnboardingScreen(
        state = state,
        onIntent = viewModel::onIntent,
    )
}