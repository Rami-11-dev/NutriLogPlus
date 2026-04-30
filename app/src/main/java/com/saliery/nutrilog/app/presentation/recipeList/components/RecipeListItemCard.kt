package com.saliery.nutrilog.app.presentation.recipeList.components

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.recipe.RecipeListItemModel
import com.saliery.nutrilog.app.presentation.helper.previewRecipeList
import com.saliery.nutrilog.app.presentation.helper.trimSmart
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.rememberHazeState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun RecipeListItemCard(
    recipe: RecipeListItemModel,
    hazeState: HazeState,
    onClick: (Long) -> Unit
) {

    Card(
        onClick = { onClick(recipe.id) },
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .fillMaxWidth()
            .then(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Modifier.hazeEffect(
                        state = hazeState,
                        style = HazeStyle(
                            blurRadius = 24.dp,
                            tint = HazeTint(OnboardingGlassTokens.GlassSurface.copy(alpha = 0.6f))
                        )
                    )
                } else Modifier
            ),
        colors = CardDefaults.cardColors(containerColor = OnboardingGlassTokens.GlassSurface),
        border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            RecipeCaloriesBadge(recipe.totalCalories)

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                    color = OnboardingGlassTokens.TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                RecipeSimpleMetaRow(
                    ingredientCount = recipe.ingredientCount,
                    updatedAt = recipe.updatedAt
                )

                RecipeMacroSummaryRow(recipe = recipe)
            }

            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = null,
                tint = OnboardingGlassTokens.TextTertiary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun RecipeCaloriesBadge(calories: Double) {
    Surface(
        modifier = Modifier.size(64.dp),
        shape = RoundedCornerShape(18.dp),
        color = OnboardingGlassTokens.Primary.copy(alpha = 0.08f),
        border = BorderStroke(1.dp, OnboardingGlassTokens.Primary.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = calories.trimSmart(),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Black),
                color = OnboardingGlassTokens.Primary
            )
            Text(
                text = stringResource(R.string.kcal_unit_str).uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                color = OnboardingGlassTokens.TextTertiary
            )
        }
    }
}

@Composable
private fun RecipeSimpleMetaRow(
    ingredientCount: Int,
    updatedAt: LocalDateTime
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ingredients_for_cooking_svgrepo_com),
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = OnboardingGlassTokens.TextTertiary
        )
        Text(
            text = pluralStringResource(R.plurals.recipe_ingredients_count, ingredientCount, ingredientCount),
            style = MaterialTheme.typography.bodySmall,
            color = OnboardingGlassTokens.TextSecondary
        )

        Text("•", color = OnboardingGlassTokens.GlassBorder)

        Text(
            text = updatedAt.toShortDateText(),
            style = MaterialTheme.typography.bodySmall,
            color = OnboardingGlassTokens.TextTertiary
        )
    }
}

@Composable
private fun RecipeMacroSummaryRow(recipe: RecipeListItemModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        MacroItem(recipe.totalProteins.trimSmart(),
            stringResource(R.string.protein_unit_short_str),
            OnboardingGlassTokens.ProteinColor)
        MacroItem(recipe.totalFats.trimSmart(),
            stringResource(R.string.fats_unit_short_str),
            OnboardingGlassTokens.FatsColor)
        MacroItem(recipe.totalCarbs.trimSmart(),
            stringResource(R.string.carbs_unit_short_str),
            OnboardingGlassTokens.CarbsColor)
    }
}

@Composable
fun MacroItem(
    value: String,
    label: String,
    accent: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .clip(CircleShape)
                .background(accent)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            color = OnboardingGlassTokens.TextPrimary
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = OnboardingGlassTokens.TextSecondary
        )
    }
}

private fun LocalDateTime.toShortDateText(): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return format(formatter)
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun RecipeListItemCardPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        RecipeListItemCard(
            recipe = previewRecipeList()[0],
            hazeState = rememberHazeState(),
            onClick = {}
        )
    }
}