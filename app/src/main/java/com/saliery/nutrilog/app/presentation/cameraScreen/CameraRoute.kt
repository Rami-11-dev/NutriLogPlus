package com.saliery.nutrilog.app.presentation.cameraScreen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CameraRoute(
    onNavigateToProduct: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: CameraViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CameraEffect.CloseScreen -> onBack()
                is CameraEffect.NavigateToProduct -> onNavigateToProduct(effect.productId)
                is CameraEffect.ShowError -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    CameraScreen(
        state = state,
        onIntent = viewModel::onIntent,
        snackbarHostState = snackbarHostState,
        onBack = { viewModel.onIntent(CameraIntent.CloseCamera) }
    )
}