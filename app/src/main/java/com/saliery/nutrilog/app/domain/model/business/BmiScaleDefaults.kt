package com.saliery.nutrilog.app.domain.model.business

import androidx.compose.ui.graphics.Color
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

object BmiScaleDefaults {

    val bands = listOf(

        ScaleBand(
            start = 0f,
            end = 16f,
            color = Color(0xFF4361EE),
            label = "Severe thinness"
        ),
        ScaleBand(
            start = 16f,
            end = 17f,
            color = Color(0xFF4895EF),
            label = "Moderate thinness"
        ),
        ScaleBand(
            start = 17f,
            end = 18.5f,
            color = Color(0xFF4CC9A6),
            label = "Mild thinness"
        ),
        ScaleBand(
            start = 18.5f,
            end = 25f,
            color = OnboardingGlassTokens.BmiGreen,
            label = "Normal"
        ),
        ScaleBand(
            start = 25f,
            end = 30f,
            color = OnboardingGlassTokens.BmiYellow,
            label = "Overweight"
        ),
        ScaleBand(
            start = 30f,
            end = 35f,
            color = Color(0xFFF28C38),
            label = "Obese I"
        ),
        ScaleBand(
            start = 35f,
            end = 40f,
            color = OnboardingGlassTokens.BmiRed,
            label = "Obese II"
        ),
        ScaleBand(
            start = 40f,
            end = 50f,
            color = Color(0xFFC94C4C),
            label = "Obese III"
        )
    )

    val ticks = listOf(
        "0", "16", "18.5", "25", "30", "35", "40", "50"
    )
}