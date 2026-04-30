package com.saliery.nutrilog.app.presentation.cameraScreen

sealed interface CameraEffect {
    data object CloseScreen : CameraEffect

    data class NavigateToProduct(val productId: String) : CameraEffect
    data class ShowError(val message: String) : CameraEffect
}