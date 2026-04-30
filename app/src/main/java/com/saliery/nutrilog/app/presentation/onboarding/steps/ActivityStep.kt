package com.saliery.nutrilog.app.presentation.onboarding.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.user.ActivityLevel
import com.saliery.nutrilog.app.presentation.common.GlassHorizontalSelectableCard
import com.saliery.nutrilog.app.presentation.common.OnboardingStepScaffold
import com.saliery.nutrilog.app.presentation.onboarding.OnboardingStep
import com.saliery.nutrilog.app.presentation.onboarding.UserDraft
import com.saliery.nutrilog.ui.theme.NutriLogTheme

@Composable
fun ActivityStep(
    draft: UserDraft,
    onActivitySelected: (ActivityLevel) -> Unit
) {

    val items = listOf(
        ActivityUiItem(
            level = ActivityLevel.LOW,
            icon = painterResource(R.drawable.computer_user_type_work_do_svgrepo_com),
            title = stringResource(R.string.low_activity_card_title),
            subtitle = stringResource(R.string.low_activity_card_subtitle)
        ),
        ActivityUiItem(
            level = ActivityLevel.LIGHT,
            icon = painterResource(R.drawable.walk_svgrepo_com_1_),
            title = stringResource(R.string.light_activity_card_title),
            subtitle = stringResource(R.string.light_activity_card_subtitle)
        ),
        ActivityUiItem(
            level = ActivityLevel.MODERATE,
            icon = painterResource(R.drawable.stretching_exercises_svgrepo_com),
            title = stringResource(R.string.moderate_activity_card_title),
            subtitle = stringResource(R.string.moderate_activity_card_subtitle)
        ),
        ActivityUiItem(
            level = ActivityLevel.HIGH,
            icon = painterResource(R.drawable.man_running_in_gym_svgrepo_com),
            title = stringResource(R.string.high_activity_card_title),
            subtitle = stringResource(R.string.high_activity_card_subtitle)
        ),
        ActivityUiItem(
            level = ActivityLevel.EXTREME,
            icon = painterResource(R.drawable.weight_lifting_svgrepo_com),
            title = stringResource(R.string.extreme_activity_card_title),
            subtitle = stringResource(R.string.extreme_activity_card_subtitle)
        )
    )

    OnboardingStepScaffold(
        step = OnboardingStep.ACTIVITY,
        stepName = stringResource(R.string.step_4_name),
        title = stringResource(R.string.step_4_title),
        subtitle = stringResource(R.string.step_4_subtitle)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items.forEach { item ->
                GlassHorizontalSelectableCard(
                    isSelected = draft.activityLevel == item.level,
                    leadingIcon = item.icon,
                    title = item.title,
                    subtitle = item.subtitle,
                    supporting = null,
                    onClick = { onActivitySelected(item.level) }
                )
            }
        }
    }
}

private data class ActivityUiItem(
    val level: ActivityLevel,
    val icon: Painter,
    val title: String,
    val subtitle: String
)

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun ActivityStepPreview() {
    var draft by remember { mutableStateOf(UserDraft()) }

    NutriLogTheme(
        darkTheme = true
    ) {
        ActivityStep(
            draft = draft,
            onActivitySelected = { selected ->
                draft = draft.copy(activityLevel = selected)
            }
        )
    }
}