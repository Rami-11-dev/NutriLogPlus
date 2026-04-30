package com.saliery.nutrilog.app.presentation.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.presentation.home.HomeIntent
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun HomeBottomNavigationBar(
    hazeState: HazeState,
    currentRoute: HomeBottomNavigationRoutes,
    onNavigateToHome: () -> Unit,
    onNavigateToRecipeList: () -> Unit,
    onNavigateToMealJournal: () -> Unit
) {
    val isDark = isSystemInDarkTheme()

    val barTint = if (isDark) {
        OnboardingGlassTokens.GlassSurfaceStrong.copy(alpha = 0.8f)
    } else {
        Color.White.copy(alpha = 0.8f)
    }

    Surface(
        modifier =
            Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .navigationBarsPadding()
                .hazeEffect(
                    state = hazeState,
                    style = HazeStyle(
                        blurRadius = 20.dp,
                        tint = HazeTint(barTint)
                    )
                ),
        color = Color.Transparent,
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(
            width = 1.dp,
            brush = Brush.verticalGradient(
                colors = listOf(Color.White.copy(alpha = 0.3f), Color.White.copy(alpha = 0.05f))
            )
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavItem(
                icon = R.drawable.home_svgrepo_com,
                label = stringResource(R.string.home_screen_str),
                isSelected = currentRoute == HomeDestination,
                onClick = onNavigateToHome
            )

            BottomNavItem(
                icon = R.drawable.recipe_cook_svgrepo_com,
                label = stringResource(R.string.recipe_str),
                isSelected = currentRoute == RecipeListDestination,
                onClick = onNavigateToRecipeList
            )

            BottomNavItem(
                icon = R.drawable.pencil_file_svgrepo_com,
                label = stringResource(R.string.meal_screen_str),
                isSelected = currentRoute == MealJournalDestination,
                onClick = onNavigateToMealJournal
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val animatedWeight by animateFloatAsState(if (isSelected) 1.2f else 1f)

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            tint = if (isSelected)
                OnboardingGlassTokens.Primary
            else
                OnboardingGlassTokens.TextTertiary,
            modifier = Modifier
                .size(24.dp)
                .scale(animatedWeight)
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 10.sp
        )
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun HomeBottomNavigationBarPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        HomeBottomNavigationBar(
            hazeState = rememberHazeState(),
            currentRoute = HomeDestination,
            onNavigateToHome = {},
            onNavigateToRecipeList = {},
            onNavigateToMealJournal = {},
        )
    }
}