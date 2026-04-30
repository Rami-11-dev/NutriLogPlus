package com.saliery.nutrilog.app.presentation.productList.components

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
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.presentation.helper.previewFoodProductModelLiteList
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun ProductListResultsContent(
    items: List<FoodProductModelLite>,
    contentPadding: PaddingValues,
    hazeState: HazeState,
    onProductClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = items,
            key = { it.id }
        ) { product ->
            ProductListItemCard(
                product = product,
                hazeState = hazeState,
                onClick = { onProductClick(product.id) }
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun ProductListResultsContentPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        ProductListResultsContent(
            items = previewFoodProductModelLiteList(),
            contentPadding = PaddingValues(4.dp),
            hazeState = rememberHazeState(),
            onProductClick = {}
        )
    }
}
