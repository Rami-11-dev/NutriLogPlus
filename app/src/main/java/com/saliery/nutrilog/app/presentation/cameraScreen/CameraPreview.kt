package com.saliery.nutrilog.app.presentation.cameraScreen

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import timber.log.Timber

@Composable
fun CameraPreview(
    isTorchOn: Boolean,
    onBarcodeDetected: (String) -> Unit
) {

    val isPreview = LocalInspectionMode.current

    if (isPreview) {
        // If current state is preview - we simulate camera

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Camera Simulation Mode",
                color = Color.White.copy(alpha = 0.5f),
                style = MaterialTheme.typography.labelLarge
            )
        }
    } else {
        // Actual camera

        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
        val analyzer = remember {
            BarcodeAnalyzer { code -> onBarcodeDetected(code) }
        }
        val cameraProvider = cameraProviderFuture.get()

        val previewView = remember { PreviewView(context) }

        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize(),
            update = {
                setupCamera(
                    context = context,
                    cameraProvider = cameraProvider,
                    previewView = previewView,
                    lifecycleOwner = lifecycleOwner,
                    analyzer = analyzer,
                    isTorchOn = isTorchOn
                )
            }
        )
    }
}

private fun setupCamera(
    context: Context,
    cameraProvider: ProcessCameraProvider,
    previewView: PreviewView,
    lifecycleOwner: LifecycleOwner,
    analyzer: BarcodeAnalyzer,
    isTorchOn: Boolean
) {

    val preview = Preview.Builder().build().also {
        it.surfaceProvider = previewView.surfaceProvider
    }

    val imageAnalysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
        .also {
            it.setAnalyzer(
                ContextCompat.getMainExecutor(context),
                analyzer
            )
        }

    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    try {
        cameraProvider.unbindAll()

        val camera = cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageAnalysis
        )

        camera.cameraControl.enableTorch(isTorchOn)
    } catch (e: Exception) {
        Timber.e("Camera use case binding failed: $e.localizedMessage")
    }
}