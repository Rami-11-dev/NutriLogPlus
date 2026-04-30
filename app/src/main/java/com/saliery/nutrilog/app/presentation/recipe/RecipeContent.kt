package com.saliery.nutrilog.app.presentation.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.app.presentation.common.ScreenErrorContent
import com.saliery.nutrilog.app.presentation.common.ScreenLoadingContent
import com.saliery.nutrilog.app.presentation.recipe.components.RecipeBasicSection
import com.saliery.nutrilog.app.presentation.recipe.components.RecipeIngredientsSection
import com.saliery.nutrilog.app.presentation.recipe.components.RecipeSummaryHeroCard
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun RecipeContent(
    state: RecipeState,
    contentPadding: PaddingValues,
    hazeState: HazeState,
    onIntent: (RecipeIntent) -> Unit
) {

    when {
        state.isLoading -> {
            ScreenLoadingContent(contentPadding = contentPadding)
        }

        state.errorMessage != null && state.recipeId != null && state.ingredients.isEmpty() -> {
            ScreenErrorContent(
                message = state.errorMessage,
                contentPadding = contentPadding,
                onRetryClick = { onIntent(RecipeIntent.RetryClicked) }
            )
        }

        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = contentPadding.calculateTopPadding() + 8.dp,
                    bottom = contentPadding.calculateBottomPadding() + 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    RecipeSummaryHeroCard(
                        summary = state.summary,
                        servingsYieldInput = state.servingsYieldInput,
                        hazeState = hazeState
                    )
                }

                item {
                    RecipeBasicSection(
                        nameInput = state.nameInput,
                        servingsYieldInput = state.servingsYieldInput,
                        hazeState = hazeState,
                        onNameChanged = {
                            onIntent(RecipeIntent.NameChanged(it))
                        },
                        onServingsYieldChanged = {
                            onIntent(RecipeIntent.ServingsYieldChanged(it))
                        }
                    )
                }

                item {
                    RecipeIngredientsSection(
                        ingredients = state.ingredients,
                        hazeState = hazeState,
                        onAddIngredientClick = {
                            onIntent(RecipeIntent.AddIngredientClicked)
                        },
                        onAmountChanged = { productId, value ->
                            onIntent(
                                RecipeIntent.IngredientAmountChanged(
                                    productId = productId,
                                    value = value
                                )
                            )
                        },
                        onCookingMethodChanged = { productId, method ->
                            onIntent(
                                RecipeIntent.IngredientCookingMethodChanged(
                                    productId = productId,
                                    method = method
                                )
                            )
                        },
                        onRemoveIngredientClick = { productId ->
                            onIntent(
                                RecipeIntent.RemoveIngredientClicked(productId)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun RecipeContentPreview() {

    val state = remember { RecipeState() }

    NutriLogTheme(
        darkTheme = true
    ) {
        RecipeContent(
            state = state,
            contentPadding = PaddingValues(),
            hazeState = rememberHazeState(),
            onIntent = {}
        )
    }
}