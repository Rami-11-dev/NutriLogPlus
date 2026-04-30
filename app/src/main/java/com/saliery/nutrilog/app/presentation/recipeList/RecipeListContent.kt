package com.saliery.nutrilog.app.presentation.recipeList

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.app.presentation.common.ScreenErrorContent
import com.saliery.nutrilog.app.presentation.common.ScreenListEmptyContent
import com.saliery.nutrilog.app.presentation.common.ScreenLoadingContent
import com.saliery.nutrilog.app.presentation.recipeList.components.RecipeListResultsContent
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun RecipeListContent(
    state: RecipeListState,
    hazeState: HazeState,
    paddingValues: PaddingValues,
    onIntent: (RecipeListIntent) -> Unit
) {

    when (val searchState = state.searchState) {
        RecipeListSearchState.Empty -> {
            ScreenListEmptyContent(
                query = state.submittedQuery.orEmpty(),
                contentPadding = paddingValues,
                onRetryClick = {
                    onIntent(RecipeListIntent.RetryClicked)
                }
            )
        }
        is RecipeListSearchState.Error -> {
            ScreenErrorContent(
                message = searchState.message,
                contentPadding = paddingValues,
                onRetryClick = {
                    onIntent(RecipeListIntent.RetryClicked)
                }
            )
        }
        RecipeListSearchState.Loading -> {
            ScreenLoadingContent(
                contentPadding = paddingValues
            )
        }
        is RecipeListSearchState.Success -> {
            RecipeListResultsContent(
                items = searchState.items,
                contentPadding = paddingValues,
                hazeState = hazeState,
                onRecipeClick = { recipeId ->
                    onIntent(RecipeListIntent.RecipeClicked(recipeId))
                }
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun RecipeListContentPreview() {

    val state = remember { RecipeListState() }

    NutriLogTheme(
        darkTheme = true
    ) {
        RecipeListContent(
            state = state,
            hazeState = rememberHazeState(),
            paddingValues = PaddingValues(4.dp),
            onIntent = {}
        )
    }
}