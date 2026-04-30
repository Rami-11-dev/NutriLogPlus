package com.saliery.nutrilog.app.presentation.cameraScreen


data class CameraState(
    val isPermissionGranted: Boolean = false,
    val activeMode: CameraMode = CameraMode.BARCODE,
    val isTorchOn: Boolean = false,
    val isProcessing: Boolean = false,
    val error: String? = null
)

enum class CameraMode {
    BARCODE, AI
}