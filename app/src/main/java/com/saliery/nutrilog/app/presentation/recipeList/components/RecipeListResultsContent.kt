package com.saliery.nutrilog.app.presentation.recipeList.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.app.domain.model.recipe.RecipeListItemModel
import com.saliery.nutrilog.app.presentation.helper.previewRecipeList
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun RecipeListResultsContent(
    items: List<RecipeListItemModel>,
    contentPadding: PaddingValues,
    hazeState: HazeState,
    onRecipeClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = items,
            key = { it.id }
        ) { recipe ->
            RecipeListItemCard(
                recipe = recipe,
                hazeState = hazeState,
                onClick = onRecipeClick
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun RecipeListResultsContentPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        RecipeListResultsContent(
            items = previewRecipeList(),
            contentPadding = PaddingValues(4.dp),
            hazeState = rememberHazeState(),
            onRecipeClick = {}
        )
    }
}