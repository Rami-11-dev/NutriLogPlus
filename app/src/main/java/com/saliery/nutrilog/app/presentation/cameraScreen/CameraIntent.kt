package com.saliery.nutrilog.app.presentation.cameraScreen

sealed interface CameraIntent {
    data object ToggleTorch : CameraIntent
    data object CloseCamera : CameraIntent

    data class OnPermissionResult(val isGranted: Boolean) : CameraIntent
    data class ChangeMode(val mode: CameraMode) : CameraIntent
    data class BarcodeDetected(val barcode: String) : CameraIntent
}