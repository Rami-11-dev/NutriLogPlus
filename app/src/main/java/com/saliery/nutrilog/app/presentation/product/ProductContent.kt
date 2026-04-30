package com.saliery.nutrilog.app.presentation.product

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.presentation.common.ScreenErrorContent
import com.saliery.nutrilog.app.presentation.common.ScreenListEmptyContent
import com.saliery.nutrilog.app.presentation.common.ScreenLoadingContent
import com.saliery.nutrilog.app.presentation.helper.previewFoodProduct
import com.saliery.nutrilog.app.presentation.product.components.ExpandableProductSection
import com.saliery.nutrilog.app.presentation.product.components.ProductHeroCard
import com.saliery.nutrilog.app.presentation.product.components.contentSections.ProductIngredientsSection
import com.saliery.nutrilog.app.presentation.product.components.contentSections.ProductMetadataSection
import com.saliery.nutrilog.app.presentation.product.components.contentSections.ProductMicronutrientsSection
import com.saliery.nutrilog.app.presentation.product.components.contentSections.ProductNutriscoreSection
import com.saliery.nutrilog.app.presentation.product.components.contentSections.ProductNutritionSection
import com.saliery.nutrilog.app.presentation.product.components.contentSections.ProductPortionsSection
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun ProductContent(
    state: ProductState,
    contentPadding: PaddingValues,
    hazeState: HazeState,
    onIntent: (ProductIntent) -> Unit
) {

    when {
        state.isLoading && state.product == null -> {
            ScreenLoadingContent(
                contentPadding = contentPadding
            )
        }

        state.errorMessage != null && state.product == null -> {
            ScreenErrorContent(
                message = state.errorMessage,
                contentPadding = contentPadding,
                onRetryClick = { onIntent(ProductIntent.RetryClicked) }
            )
        }

        state.isProductNotFound -> {
            ScreenListEmptyContent(
                query = state.productId,
                contentPadding = contentPadding,
                onRetryClick = { onIntent(ProductIntent.RetryClicked) }
            )
        }

        state.product != null -> {
            val product = state.product

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = contentPadding
            ) {
                item {
                    ProductHeroCard(
                        product = product,
                    )
                }

                item {
                    ExpandableProductSection(
                        title = stringResource(R.string.product_nutrition_str),
                        expanded = ProductSection.NUTRITION in state.expandedSections,
                        icon = painterResource(R.drawable.fire_svgrepo_com),
                        hazeState = hazeState,
                        onToggle = {
                            onIntent(ProductIntent.SectionToggled(ProductSection.NUTRITION))
                        }
                    ) {
                        ProductNutritionSection(facts = product.nutritionFacts)
                    }
                }

                if (product.micronutrients != null) {
                    item {
                        ExpandableProductSection(
                            title = stringResource(R.string.microelements_str),
                            expanded = ProductSection.MICRONUTRIENTS in state.expandedSections,
                            icon = painterResource(R.drawable.balance_svgrepo_com),
                            hazeState = hazeState,
                            onToggle = {
                                onIntent(
                                    ProductIntent.SectionToggled(ProductSection.MICRONUTRIENTS)
                                )
                            }
                        ) {
                            ProductMicronutrientsSection(micro = product.micronutrients)
                        }
                    }
                }

                if (product.portions.isNotEmpty()) {
                    item {
                        ExpandableProductSection(
                            title = stringResource(R.string.portion_str),
                            expanded = ProductSection.PORTIONS in state.expandedSections,
                            icon = painterResource(R.drawable.portion_pie_chart_svgrepo_com),
                            hazeState = hazeState,
                            onToggle = {
                                onIntent(ProductIntent.SectionToggled(ProductSection.PORTIONS))
                            }
                        ) {
                            ProductPortionsSection(product = product)
                        }
                    }
                }

                if (product.ingredients.isNotEmpty() || product.allergens.isNotEmpty()
                    || product.additives.isNotEmpty() || product.traces.isNotEmpty()
                ) {
                    item {
                        ExpandableProductSection(
                            title = stringResource(R.string.product_composition_str),
                            expanded = ProductSection.INGREDIENTS in state.expandedSections,
                            icon = painterResource(R.drawable.carrot_svgrepo_com),
                            hazeState = hazeState,
                            onToggle = {
                                onIntent(ProductIntent.SectionToggled(ProductSection.INGREDIENTS))
                            }
                        ) {
                            ProductIngredientsSection(product = product)
                        }
                    }
                }

                if (product.nutriscoreSummary != null || product.nutriscoreComponents.isNotEmpty()) {
                    item {
                        ExpandableProductSection(
                            title = "Nutri-Score",
                            expanded = ProductSection.NUTRISCORE in state.expandedSections,
                            icon = painterResource(R.drawable.medicine_symbol_1_svgrepo_com),
                            hazeState = hazeState,
                            onToggle = {
                                onIntent(ProductIntent.SectionToggled(ProductSection.NUTRISCORE))
                            }
                        ) {
                            ProductNutriscoreSection(product = product)
                        }
                    }
                }

                if (product.sourceMetadata != null) {
                    item {
                        ExpandableProductSection(
                            title = stringResource(R.string.source_and_metadata_str),
                            expanded = ProductSection.METADATA in state.expandedSections,
                            icon = painterResource(R.drawable.fingerprint_alt_svgrepo_com),
                            hazeState = hazeState,
                            onToggle = {
                                onIntent(ProductIntent.SectionToggled(ProductSection.METADATA))
                            }
                        ) {
                            ProductMetadataSection(metadata = product.sourceMetadata)
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
fun ProductContentPreview() {

    val hazeState = rememberHazeState()

    NutriLogTheme(
        darkTheme = true
    ) {
        ProductContent(
            state = ProductState(
                product = previewFoodProduct(),
                isLoading = false,
                isRefreshing = false,
                errorMessage = null,
                isProductNotFound = false
            ),
            contentPadding = PaddingValues(),
            onIntent = {},
            hazeState = hazeState
        )
    }
}