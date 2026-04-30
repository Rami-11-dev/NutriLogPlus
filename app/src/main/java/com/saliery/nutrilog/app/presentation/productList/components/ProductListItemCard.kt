package com.saliery.nutrilog.app.presentation.productList.components

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.domain.model.product.ProductDataSourceEnum
import com.saliery.nutrilog.app.presentation.helper.previewFoodProductModelLiteList
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.rememberHazeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListItemCard(
    product: FoodProductModelLite,
    hazeState: HazeState,
    onClick: (String) -> Unit
) {

    Card(
        onClick = { onClick(product.id) },
        shape = RoundedCornerShape(24.dp), // A little less than a Hero block
        modifier = Modifier
            .padding(horizontal = 16.dp) // Outer screen boards padding
            .fillMaxWidth()
            .then(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Modifier
                        .hazeEffect(
                            state = hazeState,
                            style = HazeStyle(
                                blurRadius = 20.dp,
                                tint = HazeTint(
                                    if (isSystemInDarkTheme()) {
                                        OnboardingGlassTokens.GlassSurfaceStrong
                                    } else {
                                        OnboardingGlassTokens.GlassSurface.copy(alpha = 0.72f)
                                    }
                                )
                            ),
                            block = null
                        )
                } else Modifier
            ),

        colors = CardDefaults.cardColors(containerColor = OnboardingGlassTokens.GlassSurface),
        border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSystemInDarkTheme()) 1.dp else 1.5.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (isSystemInDarkTheme()) {
                            OnboardingGlassTokens.GlassSurfaceStrong
                        } else {
                            OnboardingGlassTokens.GlassSurface
                        }
                    )
            ) {
                if (product.displayImage != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.displayImage)
                            .crossfade(true)
                            .build(),
                        contentDescription = product.productName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {

                    val icon = when (product.source) {
                        ProductDataSourceEnum.OPEN_FOOD_FACTS -> painterResource(R.drawable.open_food_facts_seeklogo__1_)
                        else -> painterResource(R.drawable.usda_logo_color)
                    }

                    Icon(
                        painter = icon,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center).size(32.dp),
                        tint = Color.Unspecified
                    )
                }

                // A little Nutri-Score badge
                product.nutritionGrade?.let { grade ->
                    NutritionGradeBadge(
                        grade = grade,
                        modifier = Modifier.align(Alignment.BottomEnd).padding(4.dp)
                    )
                }
            }

            // Product crucial information
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = product.productName ?: "Неизвестный продукт",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnboardingGlassTokens.TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if (!product.productBrand.isNullOrBlank()) {
                    Text(
                        text = product.productBrand,
                        style = MaterialTheme.typography.labelMedium,
                        color = OnboardingGlassTokens.TextSecondary,
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Compact macros representing
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MacroMiniInfo(label = stringResource(R.string.kcal_unit_str), value = product.caloriesPer100g?.toInt()?.toString() ?: "—")

                    VerticalDivider(modifier = Modifier.height(10.dp), color = OnboardingGlassTokens.GlassBorder)

                    MacroMiniInfo(label = stringResource(R.string.protein_unit_short_str), value = product.proteinsPer100g?.toString() ?: "—")
                    MacroMiniInfo(label = stringResource(R.string.fats_unit_short_str), value = product.fatsPer100g?.toString() ?: "—")
                    MacroMiniInfo(label = stringResource(R.string.carbs_unit_short_str), value = product.carbsPer100g?.toString() ?: "—")
                }
            }
        }
    }
}


@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
)
@Composable
private fun ProductListItemCardPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        ProductListItemCard(
            product = previewFoodProductModelLiteList().first(),
            hazeState = rememberHazeState(),
            onClick = {}
        )
    }
}