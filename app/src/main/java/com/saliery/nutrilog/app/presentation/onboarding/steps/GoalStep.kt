package com.saliery.nutrilog.app.presentation.onboarding.steps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.user.Goal
import com.saliery.nutrilog.app.presentation.common.GlassHorizontalSelectableCard
import com.saliery.nutrilog.app.presentation.common.OnboardingStepScaffold
import com.saliery.nutrilog.app.presentation.onboarding.OnboardingStep
import com.saliery.nutrilog.app.presentation.onboarding.UserDraft
import com.saliery.nutrilog.ui.theme.NutriLogTheme

@Composable
fun GoalStep(
    draft: UserDraft,
    onGoalSelected: (Goal) -> Unit
) {
    val items = listOf(
        GoalUiItem(
            goal = Goal.LOSE_WEIGHT,
            icon = painterResource(R.drawable.weight_down_svgrepo_com),
            title = stringResource(R.string.lose_weight_goal_title),
            subtitle = stringResource(R.string.lose_weight_goal_subtitle),
        ),
        GoalUiItem(
            goal = Goal.MAINTAIN,
            icon = painterResource(R.drawable.balance_svgrepo_com),
            title = stringResource(R.string.maintain_goal_title),
            subtitle = stringResource(R.string.maintain_goal_subtitle)
        ),
        GoalUiItem(
            goal = Goal.GAIN_WEIGHT,
            icon = painterResource(R.drawable.weight_up_svgrepo_com),
            title = stringResource(R.string.gain_weight_goal_title),
            subtitle = stringResource(R.string.gain_weight_goal_subtitle)
        )
    )

    OnboardingStepScaffold(
        step = OnboardingStep.GOAL,
        stepName = stringResource(R.string.step_5_name),
        title = stringResource(R.string.step_5_title),
        subtitle = stringResource(R.string.step_5_subtitle)
    ) {
        items.forEach { item ->
            GlassHorizontalSelectableCard(
                isSelected = draft.goal == item.goal,
                leadingIcon = item.icon,
                title = item.title,
                subtitle = item.subtitle,
                onClick = { onGoalSelected(item.goal) }
            )
        }
    }
}

private data class GoalUiItem(
    val goal: Goal,
    val icon: Painter,
    val title: String,
    val subtitle: String? = null
)

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun GoalStepPreview() {
    var draft by remember { mutableStateOf(UserDraft()) }

    NutriLogTheme(
        darkTheme = true
    ) {
        GoalStep(
            draft = draft,
            onGoalSelected = { goal ->
                draft = draft.copy(goal = goal)
            }
        )
    }
}