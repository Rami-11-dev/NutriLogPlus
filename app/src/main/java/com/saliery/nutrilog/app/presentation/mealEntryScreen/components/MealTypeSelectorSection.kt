package com.saliery.nutrilog.app.presentation.mealEntryScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.entries.LocalMealType
import com.saliery.nutrilog.app.presentation.common.GlassCard
import com.saliery.nutrilog.app.presentation.helper.toLabel
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun MealTypeSelectorSection(
    selectedType: LocalMealType,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    onMealTypeSelected: (LocalMealType) -> Unit
) {
    GlassCard(
        modifier = modifier.fillMaxWidth(),
        hazeState = hazeState,
        contentPadding = PaddingValues(18.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Приём пищи",
                style = MaterialTheme.typography.titleMedium,
                color = OnboardingGlassTokens.TextPrimary
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LocalMealType.entries.forEach { type ->
                    MealTypeChip(
                        selected = selectedType == type,
                        type = type,
                        onClick = { onMealTypeSelected(type) }
                    )
                }
            }
        }
    }
}

@Composable
private fun MealTypeChip(
    selected: Boolean,
    type: LocalMealType,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = if (selected) {
            OnboardingGlassTokens.PrimarySoft
        } else {
            OnboardingGlassTokens.GlassSurface
        },
        border = BorderStroke(
            1.dp,
            if (selected) OnboardingGlassTokens.SelectedBorder
            else OnboardingGlassTokens.GlassBorder
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = getMealTypeIcon(type),
                contentDescription = null,
                tint = if (selected) {
                    OnboardingGlassTokens.SelectedIcon
                } else {
                    OnboardingGlassTokens.TextSecondary
                },
                modifier = Modifier.size(18.dp)
            )

            Text(
                text = type.toLabel(),
                style = MaterialTheme.typography.labelLarge,
                color = if (selected) {
                    OnboardingGlassTokens.TextPrimary
                } else {
                    OnboardingGlassTokens.TextSecondary
                }
            )
        }
    }
}

@Composable
private fun getMealTypeIcon(type: LocalMealType): Painter {
    return when (type) {
        LocalMealType.BREAKFAST -> painterResource(R.drawable.cup_first_svgrepo_com)
        LocalMealType.LUNCH -> painterResource(R.drawable.ingredients_for_cooking_svgrepo_com)
        LocalMealType.DINNER -> painterResource(R.drawable.restaurant_waiter_svgrepo_com)
        LocalMealType.SNACK -> painterResource(R.drawable.lightning_bolt_svgrepo_com)
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "ru",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun MealTypeSelectorSectionPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        MealTypeSelectorSection(
            selectedType = LocalMealType.LUNCH,
            hazeState = rememberHazeState(),
            onMealTypeSelected = {}
        )
    }
}