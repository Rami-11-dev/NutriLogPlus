package com.saliery.nutrilog.app.presentation.product.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun ProductBottomActionBar(
    enabled: Boolean,
    hazeState: HazeState?,
    onAddToMealClick: () -> Unit
) {

    val borderColor = OnboardingGlassTokens.GlassBorder

    Box(
        modifier = Modifier
            .fillMaxWidth()
            // Glass effect
            .then(
                if (hazeState != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Modifier.hazeEffect(
                        state = hazeState,
                        style = HazeStyle.Unspecified,
                        block = null
                    )
                } else Modifier
            )
            .background(OnboardingGlassTokens.GlassSurface.copy(alpha = 0.7f))

            .drawBehind {
                drawLine(
                    color = borderColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = OnboardingGlassTokens.Primary,
                contentColor = Color.White,
                disabledContainerColor = OnboardingGlassTokens.Primary.copy(alpha = 0.3f)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            enabled = enabled,
            onClick = onAddToMealClick
        ) {
            Icon(Icons.Rounded.Add, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Добавить в приём пищи",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun ProductBottomActionBarPreview() {

    val hazeState = rememberHazeState()

    NutriLogTheme(
        darkTheme = true
    ) {
        ProductBottomActionBar(
            enabled = true,
            onAddToMealClick = {},
            hazeState = hazeState
        )
    }
}