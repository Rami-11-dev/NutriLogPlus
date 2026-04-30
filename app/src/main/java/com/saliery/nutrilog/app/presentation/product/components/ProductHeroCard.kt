package com.saliery.nutrilog.app.presentation.product.components

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.product.FoodProductModel
import com.saliery.nutrilog.app.presentation.helper.previewFoodProduct
import com.saliery.nutrilog.app.presentation.helper.trimSmart
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect

@Composable
fun ProductHeroCard(
    product: FoodProductModel,
    modifier: Modifier = Modifier,
    hazeState: HazeState? = null
) {

    val isDark = isSystemInDarkTheme()

    Card(
        shape = RoundedCornerShape(28.dp),
        modifier = modifier
            .padding(16.dp)
            .then(
                if (hazeState != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Modifier.hazeEffect(
                        state = hazeState,
                        style = HazeStyle.Unspecified,
                        block = null
                    )
                } else Modifier
            ),
        colors = CardDefaults.cardColors(
            containerColor = OnboardingGlassTokens.GlassSurface // Используем наш токен
        ),
        border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder), // Тонкая граница стекла
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            Box(modifier = Modifier.height(240.dp)) {
                ProductHeroImagePager(
                    images = product.images,
                    productName = product.name,
                    source = product.source,
                    modifier = Modifier.height(260.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    if (isDark) Color.Black.copy(alpha = 0.5f)
                                    else MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
                                )
                            )
                        )
                )
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = product.name ?: stringResource(R.string.no_product_name_str),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = OnboardingGlassTokens.TextPrimary
                    )
                    if (!product.brand.isNullOrBlank()) {
                        Text(
                            text = product.brand.uppercase(),
                            style = MaterialTheme.typography.labelMedium.copy(letterSpacing = 1.sp),
                            color = OnboardingGlassTokens.TextSecondary
                        )
                    }
                }

                ProductHeroTagsRow(product = product)

                HorizontalDivider(color = OnboardingGlassTokens.GlassBorder)

                ProductHeroMacroSummary(product = product)
            }
        }
    }
}

@Composable
private fun ProductHeroTagsRow(product: FoodProductModel) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Data source (untranslatable)
        ProductInfoChip(text = product.source.name.replace("_", " "), containerColor = Color.White.copy(alpha = 0.1f))

        // Nutri-Score with color logic (untranslatable)
        product.nutritionGrade?.let { grade ->
            val scoreColor = when (grade.lowercase()) {
                "a" -> Color(0xFF038141)
                "b" -> Color(0xFF85BB2F)
                "c" -> Color(0xFFFECB02)
                "d" -> Color(0xFFEE8100)
                else -> Color(0xFFE63E11)
            }
            ProductInfoChip(text = "Score $grade", containerColor = scoreColor, contentColor = Color.White)
        }

        // NOVA group (untranslatable)
        product.novaGroup?.let {
            ProductInfoChip(text = "NOVA $it", containerColor = Color(0xFF2196F3).copy(alpha = 0.2f))
        }
    }
}

@Composable
private fun ProductInfoChip(
    text: String,
    containerColor: Color = OnboardingGlassTokens.GlassSurfaceStrong,
    contentColor: Color = OnboardingGlassTokens.TextPrimary
) {
    Surface(
        shape = CircleShape,
        color = containerColor,
        border = BorderStroke(0.5.dp, OnboardingGlassTokens.GlassBorder)
    ) {
        Text(
            text = text.uppercase(),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = contentColor
        )
    }
}

@Composable
private fun ProductHeroMacroSummary(product: FoodProductModel) {
    val facts = product.nutritionFacts
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val unit = stringResource(R.string.grams_unit_short)

        MacroElement(stringResource(R.string.kcal_unit_str), facts.kcal?.trimSmart() ?: "—", MaterialTheme.colorScheme.primary)
        MacroElement(stringResource(R.string.protein_str), "${facts.protein?.trimSmart() ?: "—"} $unit", Color(0xFF9CCC65))
        MacroElement(stringResource(R.string.fat_str), "${facts.totalFat?.trimSmart() ?: "—"} $unit", Color(0xFFFFB74D))
        MacroElement(stringResource(R.string.carbs_str), "${facts.carbohydrates?.trimSmart() ?: "—"} $unit", Color(0xFF4FC3F7))
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun ProductHeroCardPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        ProductHeroCard(
            product = previewFoodProduct()
        )
    }
}