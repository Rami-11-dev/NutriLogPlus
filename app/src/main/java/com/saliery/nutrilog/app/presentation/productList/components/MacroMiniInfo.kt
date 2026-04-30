package com.saliery.nutrilog.app.presentation.productList.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun MacroMiniInfo(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = value, style = MaterialTheme.typography.labelLarge, color = OnboardingGlassTokens.TextPrimary)
        Spacer(Modifier.width(2.dp))
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = OnboardingGlassTokens.TextSecondary)
    }
}