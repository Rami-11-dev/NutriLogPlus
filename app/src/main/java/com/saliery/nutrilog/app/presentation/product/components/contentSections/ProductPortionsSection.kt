package com.saliery.nutrilog.app.presentation.product.components.contentSections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.product.FoodProductModel
import com.saliery.nutrilog.app.domain.model.product.Portion
import com.saliery.nutrilog.app.presentation.helper.previewFoodProduct
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun ProductPortionsSection(product: FoodProductModel) {

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        // ---- USDA portions ----
        if (product.portions.isNotEmpty()) {
            Text(
                text = stringResource(R.string.portion_size_title),
                style = MaterialTheme.typography.labelLarge,
                color = OnboardingGlassTokens.TextTertiary
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                product.portions.forEach { portion ->
                    PortionItemRow(portion)
                }
            }
        }

        // ---- Open Food Facts package info ----
        product.packageInfo?.let { info ->
            if (product.portions.isNotEmpty()) {
                HorizontalDivider(color = OnboardingGlassTokens.GlassBorder)
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.total_package_quantity),
                    style = MaterialTheme.typography.labelLarge,
                    color = OnboardingGlassTokens.TextTertiary
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(OnboardingGlassTokens.GlassSurfaceStrong, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.scale_svgrepo_com), // Иконка коробки/упаковки
                            contentDescription = null,
                            tint = OnboardingGlassTokens.Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = info.label ?: "${info.quantity ?: ""} ${info.unit ?: ""}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = OnboardingGlassTokens.TextPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PortionItemRow(portion: Portion) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Dynamic portion type icon
            val icon = when {
                portion.description.contains("cup", ignoreCase = true) -> painterResource(R.drawable.cup_svgrepo_com)
                portion.description.contains("spoon,tsp,teaspoon,tbs,tablespoon", ignoreCase = true) -> painterResource(R.drawable.spoon_svgrepo_com)
                portion.description.contains("pint,quart,gallon", ignoreCase = true) -> painterResource(R.drawable.pint_of_beer_beer_mug_svgrepo_com)
                portion.description.contains("fruit", ignoreCase = true) -> painterResource(R.drawable.apple_6_svgrepo_com)
                portion.description.contains("vegetable", ignoreCase = true) -> painterResource(R.drawable.carrot_svgrepo_com)
                portion.description.contains("leg,poultry,breast,thigh", ignoreCase = true) -> painterResource(R.drawable.poultry_leg_svgrepo_com)
                portion.description.contains("item,portion,slice", ignoreCase = true) -> painterResource(R.drawable.box_svgrepo_com)
                else -> painterResource(R.drawable.unknown_filled_svgrepo_com)
            }

            Icon(
                painter = icon,
                contentDescription = null,
                tint = OnboardingGlassTokens.TextSecondary.copy(alpha = 0.6f),
                modifier = Modifier.size(18.dp)
            )

            Column {
                Text(
                    text = portion.description.ifBlank { portion.measureUnit }.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnboardingGlassTokens.TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (portion.amount > 0) {
                    Text(
                        text = stringResource(
                            R.string.portion_description_template,
                            portion.amount,
                            portion.measureUnit
                        ),
                        style = MaterialTheme.typography.labelSmall,
                        color = OnboardingGlassTokens.TextTertiary
                    )
                }
            }
        }

        Surface(
            shape = CircleShape,
            color = OnboardingGlassTokens.Primary.copy(alpha = 0.1f),
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.weight_grams_suffix, portion.gramWeight),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = OnboardingGlassTokens.Primary
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun ProductPortionsSectionPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        ProductPortionsSection(
            product = previewFoodProduct()
        )
    }
}