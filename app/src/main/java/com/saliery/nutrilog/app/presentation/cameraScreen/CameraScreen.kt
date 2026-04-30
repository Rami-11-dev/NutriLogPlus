package com.saliery.nutrilog.app.presentation.cameraScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.presentation.cameraScreen.components.CameraPermissionPlaceholder
import com.saliery.nutrilog.app.presentation.mealEntryScreen.components.GlassActionButton
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun CameraScreen(
    state: CameraState,
    onIntent: (CameraIntent) -> Unit,
    snackbarHostState: SnackbarHostState,
    onBack: () -> Unit
) {
    val hazeState = rememberHazeState()
    val buttonSize = 40.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OnboardingGlassTokens.ScreenBackground)
    ) {
        if (state.isPermissionGranted) {
            CameraPreview(
                isTorchOn = state.isTorchOn,
                onBarcodeDetected = { code ->
                    onIntent(CameraIntent.BarcodeDetected(code))
                }
            )

            ScannerOverlay(isProcessing = state.isProcessing)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GlassActionButton(
                    icon = painterResource(R.drawable.cross_svgrepo_com),
                    hazeState = hazeState,
                    buttonSize = buttonSize,
                    onClick = onBack
                )

                GlassActionButton(
                    icon = painterResource(R.drawable.lightning_svgrepo_com),
                    hazeState = hazeState,
                    buttonSize = buttonSize,
                    onClick = { onIntent(CameraIntent.ToggleTorch) },
                    tint = if (state.isTorchOn) OnboardingGlassTokens.Primary else OnboardingGlassTokens.TextPrimary
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(bottom = 40.dp)
            ) {
                CameraModeSelector(
                    activeMode = state.activeMode,
                    onModeSelected = { mode -> onIntent(CameraIntent.ChangeMode(mode)) }
                )
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.TopCenter)
            )

        } else {
            CameraPermissionPlaceholder(
                onGrantClick = { onIntent(CameraIntent.OnPermissionResult(true)) },
                onBackClick = onBack
            )
        }
    }
}

@Composable
private fun ScannerOverlay(isProcessing: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "laser")
    val laserOffset by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laserPosition"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(260.dp)
                .border(
                    width = 1.dp,
                    color = OnboardingGlassTokens.GlassBorder.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            if (!isProcessing) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(2.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = 260.dp * laserOffset)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    OnboardingGlassTokens.Primary,
                                    Color.Transparent
                                )
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun CameraModeSelector(
    activeMode: CameraMode,
    onModeSelected: (CameraMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = OnboardingGlassTokens.GlassSurfaceStrong.copy(alpha = 0.7f),
        border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder),
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            CameraModeTab(
                text = "Штрих-код",
                isSelected = activeMode == CameraMode.BARCODE,
                onClick = { onModeSelected(CameraMode.BARCODE) }
            )

            CameraModeTab(
                text = "AI Режим",
                isSelected = activeMode == CameraMode.AI,
                enabled = false, // Not implemented yet
                onClick = { onModeSelected(CameraMode.AI) }
            )
        }
    }
}

@Composable
private fun CameraModeTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) OnboardingGlassTokens.Primary else Color.Transparent,
        label = "tabBackground"
    )
    val contentColor by animateColorAsState(
        targetValue = when {
            !enabled -> OnboardingGlassTokens.TextTertiary
            isSelected -> Color.White
            else -> OnboardingGlassTokens.TextPrimary
        },
        label = "tabContent"
    )

    Surface(
        onClick = onClick,
        enabled = enabled,
        shape = CircleShape,
        color = backgroundColor,
        modifier = Modifier.height(36.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = contentColor
            )

            if (!enabled) {
                // AI recognition model not ready yet, so its unavailable
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp).align(Alignment.TopEnd).offset(x = 8.dp, y = (-3).dp),
                    tint = OnboardingGlassTokens.TextTertiary
                )
            }
        }
    }
}

@Preview(showSystemUi = true, name = "Access Granted")
@Composable
private fun CameraScreenGrantedPreview() {
    NutriLogTheme(darkTheme = true) {
        CameraScreen(
            state = CameraState(
                isPermissionGranted = true,
                activeMode = CameraMode.BARCODE,
                isProcessing = false
            ),
            onIntent = {},
            onBack = {},
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}

@Preview(showSystemUi = true, name = "Access Denied")
@Composable
private fun CameraScreenDeniedPreview() {
    NutriLogTheme(darkTheme = true) {
        CameraScreen(
            state = CameraState(
                isPermissionGranted = false
            ),
            onIntent = {},
            onBack = {},
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}