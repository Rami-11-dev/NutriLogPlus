package com.saliery.nutrilog.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun GlassDateField(
    title: String,
    value: String = "Select your birthday",
    supporting: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(24.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 50.dp,
                shape = shape,
                ambientColor = OnboardingGlassTokens.Glow,
                spotColor = OnboardingGlassTokens.Glow
            )
            .clip(shape)
            .background(OnboardingGlassTokens.GlassSurfaceStrong)
            .border(2.dp, OnboardingGlassTokens.GlassBorder, shape)
            .clickable(onClick = onClick)
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = OnboardingGlassTokens.TextSecondary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineSmall,
                    color = OnboardingGlassTokens.TextPrimary
                )
                Icon(
                    imageVector = Icons.TwoTone.DateRange,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            Text(
                text = supporting,
                style = MaterialTheme.typography.bodyMedium,
                color = OnboardingGlassTokens.TextSecondary
            )
        }
    }
}

@Preview()
@Composable
private fun GlassDateFieldPreview() {
    NutriLogTheme(
        darkTheme = true
    ) {
        GlassDateField(
            title = "title",

            supporting = "supporting",
            onClick = {},
        )
    }
}