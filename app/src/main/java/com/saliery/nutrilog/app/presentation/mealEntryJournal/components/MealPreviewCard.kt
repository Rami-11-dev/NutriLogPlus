package com.saliery.nutrilog.app.presentation.mealEntryJournal.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.entries.MealEntryPreviewModel
import com.saliery.nutrilog.app.presentation.common.GlassCard
import com.saliery.nutrilog.app.presentation.helper.previewMealEntryPreviewModel
import com.saliery.nutrilog.app.presentation.helper.toFormattedTime
import com.saliery.nutrilog.app.presentation.helper.toLabel
import com.saliery.nutrilog.app.presentation.helper.trimSmart
import com.saliery.nutrilog.app.presentation.recipeList.components.MacroItem
import com.saliery.nutrilog.app.presentation.recipeList.components.RecipeCaloriesBadge
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState
import java.time.LocalDateTime

@Composable
fun MealPreviewCard(
    meal: MealEntryPreviewModel,
    hazeState: HazeState,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    GlassCard(
        hazeState = hazeState,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            RecipeCaloriesBadge(meal.caloriesTotal)

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = meal.mealType.toLabel(),
                    style = MaterialTheme.typography.titleMedium,
                    color = OnboardingGlassTokens.TextPrimary
                )

                MetaDataRow(meal.dateTime)

                MealMacroRow(meal = meal)
            }

            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = null,
                tint = OnboardingGlassTokens.TextTertiary
            )
        }
    }
}

@Composable
private fun MealMacroRow(meal: MealEntryPreviewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MacroItem(meal.proteinsTotal.trimSmart(),
            stringResource(R.string.protein_unit_short_str),
            OnboardingGlassTokens.ProteinColor)
        MacroItem(meal.fatsTotal.trimSmart(),
            stringResource(R.string.fats_unit_short_str),
            OnboardingGlassTokens.FatsColor)
        MacroItem(meal.carbsTotal.trimSmart(),
            stringResource(R.string.carbs_unit_short_str),
            OnboardingGlassTokens.CarbsColor)
    }
}

@Composable
private fun MetaDataRow(createdAt: LocalDateTime) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.time_svgrepo_com),
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = OnboardingGlassTokens.TextTertiary
        )
        Text(
            text = stringResource(R.string.created_at_str, createdAt.toFormattedTime()),
            style = MaterialTheme.typography.bodySmall,
            color = OnboardingGlassTokens.TextSecondary
        )
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "ru"
)
@Composable
private fun MealPreviewCardPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        MealPreviewCard(
            meal = previewMealEntryPreviewModel().first(),
            hazeState = rememberHazeState(),
            onClick = {},
            onDelete = {}
        )
    }
}