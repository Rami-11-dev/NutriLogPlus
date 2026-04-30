package com.saliery.nutrilog.app.presentation.product.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun MacroElement(label: String, value: String, accentColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
            color = OnboardingGlassTokens.TextPrimary
        )
        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .height(3.dp)
                .width(24.dp)
                .background(accentColor, CircleShape)
        )
        Text(
            text = label,
            modifier = Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
            color = OnboardingGlassTokens.TextTertiary
        )
    }
}