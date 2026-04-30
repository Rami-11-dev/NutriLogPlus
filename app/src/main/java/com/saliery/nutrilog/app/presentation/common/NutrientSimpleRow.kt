package com.saliery.nutrilog.app.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.saliery.nutrilog.app.presentation.helper.trimSmart
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun NutritionSimpleRow(label: String, value: Double?, unit: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = OnboardingGlassTokens.TextSecondary
        )
        Text(
            text = "${value?.trimSmart() ?: "—"} $unit",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = OnboardingGlassTokens.TextPrimary
        )
    }
}