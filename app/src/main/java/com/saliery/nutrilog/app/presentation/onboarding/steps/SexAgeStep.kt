package com.saliery.nutrilog.app.presentation.onboarding.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.helper.JavaLocalDateSaver
import com.saliery.nutrilog.app.domain.helper.KotlinLocalDateSaver
import com.saliery.nutrilog.app.domain.helper.calculateAgeFromLocalDate
import com.saliery.nutrilog.app.domain.model.user.Sex
import com.saliery.nutrilog.app.presentation.common.GlassDateField
import com.saliery.nutrilog.app.presentation.common.GlassSelectableCard
import com.saliery.nutrilog.app.presentation.common.GlassWheelDatePicker
import com.saliery.nutrilog.app.presentation.common.OnboardingStepScaffold
import com.saliery.nutrilog.app.presentation.onboarding.OnboardingStep
import com.saliery.nutrilog.app.presentation.onboarding.UserDraft
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import network.chaintech.kmp_date_time_picker.utils.now


@Composable
fun SexAgeStep(
    draft: UserDraft,
    onSexSelected: (Sex) -> Unit,
    onBirthDateSelected: (LocalDate) -> Unit
) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    val selectedLocalDate = draft.birthDate
    val derivedAge = selectedLocalDate?.let(::calculateAgeFromLocalDate)

    OnboardingStepScaffold(
        step = OnboardingStep.SEX_AGE,
        stepName = stringResource(R.string.step_2_name),
        title = stringResource(R.string.step_2_title),
        subtitle = stringResource(R.string.step_2_explanation_str)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            GlassSelectableCard(
                selected = draft.sex == Sex.MALE,
                title = stringResource(R.string.male_str),
                subtitle = null,
                icon = painterResource(R.drawable.male),
                iconTint = Color.Cyan.copy(alpha = 0.50f),
                modifier = Modifier
                    .shadow(
                        elevation = 40.dp,
                        shape = RoundedCornerShape(22.dp),
                        ambientColor = Color.Cyan.copy(alpha = 0.30f),
                        spotColor = Color.Blue.copy(alpha = 0.30f)
                    )
                    .weight(1f),
                onClick = { onSexSelected(Sex.MALE) }
            )
            GlassSelectableCard(
                selected = draft.sex == Sex.FEMALE,
                title = stringResource(R.string.female_str),
                subtitle = null,
                icon = painterResource(R.drawable.female_24px),
                iconTint = Color.Magenta.copy(alpha = 0.50f),
                modifier = Modifier
                    .weight(1f)
                    .shadow(
                        elevation = 40.dp,
                        shape = RoundedCornerShape(22.dp),
                        ambientColor = Color.Red.copy(alpha = 0.30f),
                        spotColor = Color.Red.copy(alpha = 0.30f)
                    ),
                onClick = { onSexSelected(Sex.FEMALE) }
            )
        }

        GlassDateField(
            title = stringResource(R.string.birthday_str),
            value = selectedLocalDate?.let(::formatDate)
                ?: LocalDate.now().let(::formatDate),
            supporting = when {
                derivedAge == null -> stringResource(R.string.tap_to_select_from_calendar)
                derivedAge > 0 -> stringResource(R.string.age_str) + ": $derivedAge"
                else -> stringResource(R.string.please_select_a_valid_date)
            },
            onClick = { showDatePicker = true }
        )

        Text(
            text = stringResource(R.string.age_is_needed_to_accurately_create_nutrition_plan_txt),
            style = MaterialTheme.typography.bodyMedium,
            color = OnboardingGlassTokens.TextSecondary
        )
    }

    GlassWheelDatePicker(
        showDatePicker = showDatePicker,
        onDoneClick = { selectedDate ->
            onBirthDateSelected(selectedDate)
            showDatePicker = false
        },
        onDismiss = {
            showDatePicker = false
        }
    )
}

private fun formatDate(date: LocalDate): String {
    val month = date.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    return "%02d $month %04d".format(date.day, date.year)
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    showBackground = false
)
@Composable
private fun SexAgeStepPreview() {
    var draft by remember { mutableStateOf(UserDraft()) }

    NutriLogTheme(
        darkTheme = true
    ) {
        SexAgeStep(
            draft = draft,
            onSexSelected = { sex ->
                draft = draft.copy(sex = sex)
            },
            onBirthDateSelected = { bd ->
                draft = draft.copy(birthDate = bd)
            }
        )
    }
}