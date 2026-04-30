package com.saliery.nutrilog.app.presentation.mealEntryScreen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.rememberHazeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealEntryTopBar(
    isNewEntry: Boolean,
    isSaving: Boolean,
    isDeleting: Boolean,
    hazeState: HazeState,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    val isDark = isSystemInDarkTheme()

    val tintColor = if (isDark) {
        OnboardingGlassTokens.GlassSurface.copy(alpha = 0.4f)
    } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
    }

    CenterAlignedTopAppBar(
        modifier =
            Modifier
                .fillMaxWidth()
                .hazeEffect(
                    state = hazeState,
                    style = HazeStyle(
                        blurRadius = 25.dp,
                        tint = HazeTint(tintColor)
                    )
                ),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        ),
        title = {
            AnimatedContent(
                targetState = isNewEntry,
                transitionSpec = { fadeIn() togetherWith fadeOut() }
            ) { targetIsNew ->
                Text(
                    text = if (targetIsNew) stringResource(R.string.new_entry_str)
                    else stringResource(R.string.meal_edit_str),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnboardingGlassTokens.TextPrimary
                )
            }
        },
        navigationIcon = {
            IconButton(
                enabled = !isSaving && !isDeleting,
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.back_btn),
                    tint = OnboardingGlassTokens.TextPrimary
                )
            }
        },
        actions = {
            if (!isNewEntry) {
                IconButton(
                    enabled = !isSaving && !isDeleting,
                    onClick = onDeleteClick
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = stringResource(R.string.recipe_delete_desc),
                        tint = OnboardingGlassTokens.BmiRed
                    )
                }
            }
        }
    )
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun MealEntryTopBarPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        MealEntryTopBar(
            isNewEntry = false,
            isSaving = false,
            isDeleting = false,
            hazeState = rememberHazeState(),
            onBackClick = {},
            onDeleteClick = {}
        )
    }
}