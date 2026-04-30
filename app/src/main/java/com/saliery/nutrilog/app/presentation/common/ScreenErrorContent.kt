package com.saliery.nutrilog.app.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun ScreenErrorContent(
    message: String,
    contentPadding: PaddingValues,
    onRetryClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = OnboardingGlassTokens.BmiRed.copy(alpha = 0.15f),
                border = BorderStroke(1.dp, OnboardingGlassTokens.BmiRed.copy(alpha = 0.3f))
            ) {
                Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = null,
                    modifier = Modifier.padding(20.dp).size(32.dp),
                    tint = OnboardingGlassTokens.BmiRed
                )
            }

            Text(
                text = stringResource(R.string.loading_error_str),
                style = MaterialTheme.typography.titleLarge,
                color = OnboardingGlassTokens.TextPrimary
            )

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = OnboardingGlassTokens.TextSecondary,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = onRetryClick,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = OnboardingGlassTokens.Primary,
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.Rounded.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.try_again_str)
                )
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun ScreenErrorContentPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        ScreenErrorContent(
            message = "Unknown error",
            contentPadding = PaddingValues(4.dp),
            onRetryClick = {}
        )
    }
}