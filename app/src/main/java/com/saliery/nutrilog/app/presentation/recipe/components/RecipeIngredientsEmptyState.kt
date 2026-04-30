package com.saliery.nutrilog.app.presentation.recipe.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun RecipeIngredientsEmptyState() {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = OnboardingGlassTokens.GlassSurface,
        border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.box_open_svgrepo_com),
                contentDescription = null,
                tint = OnboardingGlassTokens.TextSecondary,
                modifier = Modifier
                    .size(50.dp)
            )

            Text(
                text = "Ингредиенты пока не добавлены",
                style = MaterialTheme.typography.bodyMedium,
                color = OnboardingGlassTokens.TextSecondary
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun RecipeIngredientsEmptyStatePreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        RecipeIngredientsEmptyState()
    }
}