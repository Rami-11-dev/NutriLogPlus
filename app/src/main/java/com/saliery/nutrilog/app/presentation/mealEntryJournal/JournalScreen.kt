package com.saliery.nutrilog.app.presentation.mealEntryJournal

import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.app.presentation.common.ScreenListEmptyContent
import com.saliery.nutrilog.app.presentation.common.ScreenLoadingContent
import com.saliery.nutrilog.app.presentation.helper.previewMealEntryPreviewModel
import com.saliery.nutrilog.app.presentation.helper.previewUser
import com.saliery.nutrilog.app.presentation.helper.toFormattedDate
import com.saliery.nutrilog.app.presentation.mealEntryJournal.components.JournalScreenTopBar
import com.saliery.nutrilog.app.presentation.mealEntryJournal.components.MealPreviewCard
import com.saliery.nutrilog.app.presentation.mealEntryScreen.MealEntrySummaryUiModel
import com.saliery.nutrilog.app.presentation.mealEntryScreen.components.MealEntrySummaryHeroCard
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun JournalScreen(
    state: JournalState,
    snackbarHostState: SnackbarHostState,
    onIntent: (JournalIntent) -> Unit
) {
    val hazeState = rememberHazeState()

    Scaffold(
        topBar = {
            JournalScreenTopBar(
                hazeState = hazeState,
                selectedDate = state.selectedDate,
                onDateChange = { onIntent(JournalIntent.ChangeDate(it)) }
            )
        },
        containerColor = OnboardingGlassTokens.ScreenBackground,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            GlassFabButton(
                onClick = { onIntent(JournalIntent.AddNewMeal) },
                hazeState = hazeState
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState)
                .background(
                    brush = Brush.verticalGradient(
                        colors = if (isSystemInDarkTheme()) {
                            listOf(
                                OnboardingGlassTokens.ScreenBackground,
                                OnboardingGlassTokens.ScreenBackground
                            )
                        } else {
                            listOf(
                                OnboardingGlassTokens.ScreenBackground,
                                OnboardingGlassTokens.GlassSurface.copy(alpha = 0.35f)
                            )
                        }
                    )
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .hazeSource(state = hazeState),
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    bottom = 100.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Summary hero card
                item {
                    MealEntrySummaryHeroCard(
                        summary = state.dailySummary,
                        hazeState = hazeState
                    )
                }

                // Meal entry list
                if (state.meals.isEmpty() && !state.isLoading) {
                    item { ScreenListEmptyContent(
                        query = state.selectedDate.toFormattedDate(),
                        contentPadding = innerPadding,
                        onRetryClick = { onIntent(JournalIntent.Refresh) }
                    ) }
                } else {
                    items(
                        items = state.meals,
                        key = { it.id }
                    ) { meal ->

                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = { value ->
                                if (value == SwipeToDismissBoxValue.EndToStart) {
                                    onIntent(JournalIntent.DeleteMeal(meal.id))
                                    true
                                } else {
                                    false
                                }
                            },
                            positionalThreshold = { distance -> distance * 0.25f }
                        )

                        val haptic = LocalHapticFeedback.current
                        LaunchedEffect(dismissState.currentValue) {
                            if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            }
                        }

                        SwipeToDismissBox(
                            state = dismissState,
                            enableDismissFromEndToStart = false,
                            backgroundContent = {
                                DismissBackground(dismissState)
                            },
                            modifier = Modifier.animateItem()
                        ) {
                            MealPreviewCard(
                                meal = meal,
                                hazeState = hazeState,
                                onClick = { onIntent(JournalIntent.EditMeal(meal.id)) },
                                onDelete = { onIntent(JournalIntent.DeleteMeal(meal.id)) }
                            )
                        }
                    }
                }
            }

            if (state.isLoading) {
                ScreenLoadingContent(innerPadding)
            }
        }
    }
}

@Composable
private fun DismissBackground(dismissState: SwipeToDismissBoxState) {

    val color by animateColorAsState(
        when (dismissState.targetValue) {
            SwipeToDismissBoxValue.EndToStart -> Color.Red.copy(alpha = 0.5f)
            else -> Color.Transparent
        },
        label = "color"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        val scale by animateFloatAsState(
            if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 0.75f else 1.2f,
            label = "scale"
        )

        Icon(
            imageVector = Icons.Rounded.Delete,
            contentDescription = "Delete",
            tint = OnboardingGlassTokens.Primary,
            modifier = Modifier.scale(scale)
        )
    }
}

@Composable
fun GlassFabButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Rounded.Add,
    hazeState: HazeState? = null
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = OnboardingGlassTokens.Primary.copy(alpha = 0.85f),
        border = BorderStroke(
            width = 1.dp,
            brush = Brush.verticalGradient(
                listOf(
                    Color.White.copy(alpha = 0.4f),
                    OnboardingGlassTokens.Primary.copy(alpha = 0.1f)
                )
            )
        ),
        shadowElevation = 8.dp,
        modifier = modifier
            .size(56.dp)
            .then(
                if (hazeState != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Modifier.hazeEffect(
                        state = hazeState,
                        style = HazeStyle(
                            blurRadius = 20.dp,
                            tint = HazeTint(Color.Transparent)
                        )
                    )
                } else Modifier
            )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Glare to set the volume
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color.White.copy(alpha = 0.2f), Color.Transparent),
                            center = Offset(20f, 20f)
                        )
                    )
            )

            Icon(
                imageVector = icon,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "ru"
)
@Composable
private fun JournalScreenPreview() {

    val state = remember { JournalState(
        user = previewUser(),
        meals = previewMealEntryPreviewModel(),
        dailySummary = MealEntrySummaryUiModel(
            totalCalories = 498.0,
            totalProteins = 23.4,
            totalFats = 12.2,
            totalCarbs = 8.2,
            caloriesTarget = 2300.0,
            proteinTarget = 123.9,
            fatTarget = 32.0,
            carbTarget = 21.3
        ),
    ) }

    NutriLogTheme(
        darkTheme = true
    ) {
        JournalScreen(
            state = state,
            snackbarHostState = SnackbarHostState(),
            onIntent = {}
        )
    }
}