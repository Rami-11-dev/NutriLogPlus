package com.saliery.nutrilog.app.presentation.recipe.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun RecipeBottomActionBar(
    isNewRecipe: Boolean,
    enabled: Boolean,
    isLoading: Boolean,
    hazeState: HazeState,
    onSaveClick: () -> Unit
) {
    val isDark = isSystemInDarkTheme()

    val barTint = if (isDark) {
        OnboardingGlassTokens.GlassSurfaceStrong.copy(alpha = 0.8f)
    } else {
        Color.White.copy(alpha = 0.8f)
    }

    Surface(
        modifier =
            Modifier
                .fillMaxWidth()
                .hazeEffect(
                    state = hazeState,
                    style = HazeStyle(
                        blurRadius = 20.dp,
                        tint = HazeTint(barTint)
                    )
                ),
        color = Color.Transparent,
        border = BorderStroke(
            width = 1.dp,
            color = OnboardingGlassTokens.GlassBorder.copy(alpha = if (isDark) 1f else 0.5f)
        )
    ) {
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                enabled = enabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = OnboardingGlassTokens.Primary,
                    contentColor = Color.White,
                    disabledContainerColor = OnboardingGlassTokens.Primary.copy(alpha = 0.3f)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 3.dp,
                        color = Color.White
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = if (isNewRecipe) Icons.Rounded.Add else Icons.Rounded.Done,
                            contentDescription = null
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = if (isNewRecipe) stringResource(R.string.recipe_save_btn)
                            else stringResource(R.string.save_changes_btn),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun RecipeBottomActionBarPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        RecipeBottomActionBar(
            isNewRecipe = true,
            enabled = true,
            isLoading = false,
            hazeState = rememberHazeState(),
            onSaveClick = {}
        )
    }
}