package com.saliery.nutrilog.app.presentation.home

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
import com.saliery.nutrilog.app.presentation.helper.previewHomeState
import com.saliery.nutrilog.app.presentation.home.components.HomeSummaryHeroCard
import com.saliery.nutrilog.app.presentation.home.components.WaterSummaryCard
import com.saliery.nutrilog.app.presentation.home.components.WeightSummaryCard
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun HomeContent(
    state: HomeState,
    paddingValues: PaddingValues,
    hazeState: HazeState,
    onIntent: (HomeIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = paddingValues.calculateTopPadding() + 16.dp,
            bottom = paddingValues.calculateBottomPadding() + 24.dp,
            start = 14.dp,
            end = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            HomeSummaryHeroCard(
                loadingState = state.mainLoading,
                model = state.summaryCard,
                hazeState = hazeState
            )
        }

        item {
            WaterSummaryCard(
                state = state.waterLoading,
                model = state.waterCard,
                hazeState = hazeState,
                glassOfWaterAdd = { onIntent(HomeIntent.AddGlassOfWaterClicked) },
                waterMlAdd = { onIntent(HomeIntent.AddCustomWaterClicked(it)) }
            )
        }

        item {
            WeightSummaryCard(
                state = state.weightLoading,
                model = state.weightCard,
                hazeState = hazeState,
                onWeightKgUpdate = { onIntent(HomeIntent.SaveWeightClicked(it)) },
                onRangeChange = { onIntent(HomeIntent.WeightTimeRangeChanged(it)) }
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun HomeContentPreview() {

    val state = remember { previewHomeState() }

    NutriLogTheme(
        darkTheme = true
    ) {
        HomeContent(
            state = state,
            paddingValues = PaddingValues(),
            hazeState = rememberHazeState(),
            onIntent = {}
        )
    }
}