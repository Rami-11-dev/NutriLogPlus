package com.saliery.nutrilog.app.presentation.product.components.contentSections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.product.FoodProductModel
import com.saliery.nutrilog.app.domain.model.product.NutriscoreComponent
import com.saliery.nutrilog.app.presentation.helper.previewFoodProduct
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun ProductNutriscoreSection(product: FoodProductModel) {
    val summary = product.nutriscoreSummary
    val components = product.nutriscoreComponents

    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

        // --- First block - common value ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(OnboardingGlassTokens.GlassSurfaceStrong, RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NutriScoreBadge(grade = summary?.grade)

            Column {
                Text(
                    text = stringResource(R.string.total_points_value, summary?.score ?: "—"),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnboardingGlassTokens.TextPrimary
                )
                Text(
                    text = stringResource(R.string.lower_the_score_the_healthier_str),
                    style = MaterialTheme.typography.labelSmall,
                    color = OnboardingGlassTokens.TextTertiary
                )
            }
        }

        // --- Second block - negative factors ---
        val negative = components.filter { it.isNegative }
        if (negative.isNotEmpty()) {
            NutriScoreGroup(
                title = stringResource(R.string.increases_the_score_str),
                titleColor = OnboardingGlassTokens.BmiRed,
                components = negative
            )
        }

        // --- Third block - positive factors ---
        val positive = components.filter { !it.isNegative }
        if (positive.isNotEmpty()) {
            NutriScoreGroup(
                title = stringResource(R.string.lower_the_score_str),
                titleColor = OnboardingGlassTokens.BmiGreen,
                components = positive
            )
        }
    }
}

@Composable
private fun NutriScoreBadge(grade: String?) {
    val char = grade?.uppercase() ?: "?"
    val color = when (char) {
        "A" -> Color(0xFF038141)
        "B" -> Color(0xFF85BB2F)
        "C" -> Color(0xFFFECB02)
        "D" -> Color(0xFFEE8100)
        "E" -> Color(0xFFE63E11)
        else -> OnboardingGlassTokens.GlassBorder
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color,
        modifier = Modifier.size(56.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = char,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                color = Color.White
            )
        }
    }
}

@Composable
private fun NutriScoreGroup(
    title: String,
    titleColor: Color,
    components: List<NutriscoreComponent>
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = titleColor
        )

        components.forEach { component ->
            NutriScoreComponentRow(component)
        }
    }
}

@Composable
private fun NutriScoreComponentRow(component: NutriscoreComponent) {
    // Progress value based on points calculation
    val progress = remember(component.points, component.maxPoints) {
        val max = component.maxPoints ?: 1
        (component.points ?: 0).toFloat() / max.toFloat()
    }

    val pointsUnit = stringResource(R.string.points_str)

    val color = if (component.isNegative) OnboardingGlassTokens.BmiYellow else OnboardingGlassTokens.BmiGreen

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = component.componentId.toNutriName(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnboardingGlassTokens.TextPrimary
                )
                Text(
                    text = "${component.value ?: 0} ${component.unit ?: ""}",
                    style = MaterialTheme.typography.labelSmall,
                    color = OnboardingGlassTokens.TextTertiary
                )
            }

            // Total points value
            Text(
                text = "${if (component.isNegative) "+" else "-"}${component.points ?: 0} $pointsUnit",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = if (component.isNegative) OnboardingGlassTokens.BmiRed else OnboardingGlassTokens.BmiGreen
            )
        }

        // Points progress-bar
        Box(
            modifier = Modifier.fillMaxWidth().height(6.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape)
                    .background(OnboardingGlassTokens.GlassBorder)
            )
            Box(
                modifier = Modifier.fillMaxWidth(progress.coerceIn(0f, 1f)).height(4.dp).clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Composable
private fun String?.toNutriName(): String = when (this) {
    "energy" -> stringResource(R.string.energy_str)
    "sugars" -> stringResource(R.string.sugars_str)
    "saturated_fat" -> stringResource(R.string.saturated_ones_str)
    "salt" -> stringResource(R.string.salt_str)
    "fiber" -> stringResource(R.string.fiber_str)
    "proteins" -> stringResource(R.string.protein_str)
    "fruits_vegetables_legumes" -> stringResource(R.string.fruits_vegetables_legumes_str)
    else -> this?.replace("_", " ")?.replaceFirstChar { it.uppercase() } ?: "—"
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun ProductNutriscoreSectionPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        ProductNutriscoreSection(
            product = previewFoodProduct()
        )
    }
}