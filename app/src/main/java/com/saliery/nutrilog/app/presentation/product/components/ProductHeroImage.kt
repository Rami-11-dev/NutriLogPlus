package com.saliery.nutrilog.app.presentation.product.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.product.FoodImageModel
import com.saliery.nutrilog.app.domain.model.product.ProductDataSourceEnum
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun ProductHeroImagePager(
    images: List<FoodImageModel>,
    productName: String?,
    source: ProductDataSourceEnum,
    modifier: Modifier = Modifier
) {
    // Images with URLs
    val displayImages = remember(images) { images.filter { it.displayUrl != null } }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            .background(OnboardingGlassTokens.GlassSurfaceStrong)
    ) {
        if (displayImages.isNotEmpty()) {
            val pagerState = rememberPagerState(pageCount = { displayImages.size })

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                pageSpacing = 0.dp
            ) { page ->
                val image = displayImages[page]

                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image.displayUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "$productName - ${image.imageType}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Image type (FRONT, PACKAGE, INGREDIENTS etc.)
                    Surface(
                        modifier = Modifier.padding(12.dp).align(Alignment.TopEnd),
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.3f)
                    ) {
                        Text(
                            text = image.imageType.name,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            // Gradient to be sure text readable
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.35f))
                        )
                    )
            )

            // Pager indicators
            if (displayImages.size > 1) {
                Row(
                    Modifier
                        .height(30.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(displayImages.size) { iteration ->
                        val color = if (pagerState.currentPage == iteration)
                            OnboardingGlassTokens.Primary
                        else
                            Color.White.copy(alpha = 0.5f)

                        val width by animateDpAsState(
                            targetValue = if (pagerState.currentPage == iteration) 18.dp else 6.dp,
                            label = "width"
                        )

                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(width = width, height = 6.dp)
                        )
                    }
                }
            }
        } else {

            ProductHeroPlaceholder(
                source = source,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun ProductHeroPlaceholder(
    source: ProductDataSourceEnum,
    modifier: Modifier = Modifier
) {
    val icon = when (source) {
        ProductDataSourceEnum.OPEN_FOOD_FACTS -> painterResource(R.drawable.open_food_facts_seeklogo__1_)
        ProductDataSourceEnum.USDA_SR_LEGACY -> painterResource(R.drawable.usda_logo_color)
        else -> painterResource(R.drawable.recipe_keeper_svgrepo_com)
    }

    Box(
        modifier = modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.88f)
                )
            )
        ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = OnboardingGlassTokens.Primary.copy(alpha = 0.15f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(18.dp).size(56.dp),
                    tint = Color.Unspecified
                )
            }

            Text(
                text = when (source) {
                    ProductDataSourceEnum.OPEN_FOOD_FACTS -> stringResource(R.string.product_picture_unavailable_warning)
                    ProductDataSourceEnum.USDA_SR_LEGACY -> stringResource(R.string.usda_standart_product_str)
                    else -> stringResource(R.string.product_picture_unavailable_warning)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = OnboardingGlassTokens.TextSecondary
            )
        }
    }
}