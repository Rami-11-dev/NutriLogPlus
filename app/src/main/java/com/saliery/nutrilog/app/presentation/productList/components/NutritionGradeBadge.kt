package com.saliery.nutrilog.app.presentation.productList.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun NutritionGradeBadge(grade: String, modifier: Modifier = Modifier) {
    val color = when(grade.lowercase()) {
        "a" -> OnboardingGlassTokens.BmiGreen
        "b" -> Color(0xFF85BB2F)
        "c" -> OnboardingGlassTokens.BmiYellow
        "d" -> Color(0xFFEE8100)
        else -> OnboardingGlassTokens.BmiRed
    }

    Surface(
        color = color,
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
    ) {
        Text(
            text = grade.uppercase(),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
            color = Color.White
        )
    }
}