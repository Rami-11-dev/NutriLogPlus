package com.saliery.nutrilog.app.presentation.cameraScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel() {

    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CameraEffect>()
    val effect = _effect.asSharedFlow()

    fun onIntent(intent: CameraIntent) {
        when (intent) {
            is CameraIntent.BarcodeDetected -> {
                handleBarcode(intent.barcode)
            }
            is CameraIntent.ChangeMode -> {
                if (intent.mode == CameraMode.BARCODE) {
                    _state.update { it.copy(activeMode = intent.mode) }
                }
            }
            CameraIntent.CloseCamera -> {
                viewModelScope.launch {
                    _effect.emit(CameraEffect.CloseScreen)
                }
            }
            is CameraIntent.OnPermissionResult -> {
                _state.update { it.copy(isPermissionGranted = intent.isGranted) }

                if (!intent.isGranted) {
                    viewModelScope.launch {
                        _effect.emit(CameraEffect.ShowError("No permission was granted"))
                    }
                }
            }
            CameraIntent.ToggleTorch -> {
                _state.update { it.copy(isTorchOn = !it.isTorchOn) }
            }
        }
    }

    private fun handleBarcode(code: String) {
        if (_state.value.isProcessing) return

        _state.update { it.copy(isProcessing = true) }

        viewModelScope.launch {
            _effect.emit(CameraEffect.NavigateToProduct(code))
        }
    }
}