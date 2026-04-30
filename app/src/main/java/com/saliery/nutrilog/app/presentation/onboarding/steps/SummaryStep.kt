package com.saliery.nutrilog.app.presentation.onboarding.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.business.DailyWaterRecommendation
import com.saliery.nutrilog.app.domain.model.business.MacroTargets
import com.saliery.nutrilog.app.domain.model.business.SummaryUiModel
import com.saliery.nutrilog.app.domain.model.user.ActivityLevel
import com.saliery.nutrilog.app.domain.model.user.Goal
import com.saliery.nutrilog.app.domain.model.user.Sex
import com.saliery.nutrilog.app.presentation.common.BaseBlock
import com.saliery.nutrilog.app.presentation.common.BmiScaleContent
import com.saliery.nutrilog.app.presentation.common.MacroTargetsChart
import com.saliery.nutrilog.app.presentation.common.OnboardingStepScaffold
import com.saliery.nutrilog.app.presentation.common.WaterScaleContent
import com.saliery.nutrilog.app.presentation.onboarding.OnboardingStep
import com.saliery.nutrilog.app.presentation.onboarding.UserDraft
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun SummaryStep(
    draft: UserDraft,
    summaryUiModel: SummaryUiModel
) {

    OnboardingStepScaffold(
        step = OnboardingStep.SUMMARY,
        stepName = stringResource(R.string.step_6_name),
        title = stringResource(R.string.step_6_title),
        subtitle = stringResource(R.string.step_6_subtitle),
        isScrollable = true
    ) {

        BaseBlock(
            title = stringResource(R.string.kcal_day_str, summaryUiModel.dailyCalories),
            subtitle = stringResource(R.string.personalized_daily_kcal_target_str),
            supporting = null,
            leadingIcon = painterResource(R.drawable.fire_svgrepo_com_1_),
            iconTint = Color(0xFFFF6B57),
            iconAmbient = Color(0x33FF6B57),
            iconSpot = Color(0x33FF6B57)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SummaryMetricRow(
                    stringResource(R.string.basal_metabolic_rate_bmr_str),
                    stringResource(R.string.kcal_total_short_str, summaryUiModel.dailyCalories)
                )
                SummaryMetricRow(
                    stringResource(R.string.activity_adjustment_str),
                    stringResource(R.string.adjustment_str, summaryUiModel.activityMultiplier)
                )
                SummaryMetricRow(
                    stringResource(R.string.goal_adjustment_str),
                    summaryUiModel.goalAdjustmentPercent.toString() + "%"
                )

                HorizontalDivider(color = OnboardingGlassTokens.TextTertiary)

                Text(
                    text = stringResource(R.string.target_kcal_day_str, summaryUiModel.dailyCalories),
                    style = MaterialTheme.typography.titleMedium,
                    color = OnboardingGlassTokens.TextPrimary
                )
            }
        }

        BaseBlock(
            title = stringResource(R.string.your_bmi_str),
            subtitle = stringResource(R.string.your_bmi_block_desc_str),
            supporting = stringResource(R.string.bmi_healthy_range_str),
            iconTint = OnboardingGlassTokens.TextSecondary,
            iconAmbient = Color.Transparent,
            iconSpot = Color.Transparent,
            leadingIcon = painterResource(R.drawable.weight_svgrepo_com)
        ) {
            BmiScaleContent(
                bmi = summaryUiModel.bmi
            )
        }

        BaseBlock(
            title = stringResource(R.string.daily_water_str),
            subtitle = stringResource(R.string.recommended_hydration_target_str),
            supporting = stringResource(R.string.daily_water_capped_str),
            iconTint = Color.Cyan.copy(alpha = 0.5f),
            iconAmbient = Color.Blue,
            iconSpot = Color.Cyan.copy(alpha = 0.2f),
            leadingIcon = painterResource(R.drawable.water_drop_svgrepo_com_2_)
        ) {
            WaterScaleContent(
                summaryUiModel.waterRecommendation.waterLiters.toFloat()
            )

            Text(
                text = stringResource(R.string.based_on_your_weight_activity_goal_str),
                style = MaterialTheme.typography.bodyMedium,
                color = OnboardingGlassTokens.TextSecondary
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SummaryMetricRow(
                    stringResource(R.string.base_water_str),
                    stringResource(R.string.recommended_liters_value_short, summaryUiModel.waterRecommendation.baseAmount)
                )
                SummaryMetricRow(
                    stringResource(R.string.age_adjustment_str),
                    stringResource(R.string.adjustment_str, summaryUiModel.waterRecommendation.ageAdjustment)
                )
                SummaryMetricRow(
                    stringResource(R.string.sex_adjustment_str),
                    stringResource(R.string.adjustment_str, summaryUiModel.waterRecommendation.sexAdjustment)
                )
                SummaryMetricRow(
                    stringResource(R.string.activity_adjustment_str),
                    stringResource(R.string.adjustment_str, summaryUiModel.waterRecommendation.activityAdjustment)
                )
                SummaryMetricRow(
                    stringResource(R.string.goal_adjustment_str),
                    stringResource(R.string.adjustment_str, summaryUiModel.waterRecommendation.goalAdjustment)
                )

                HorizontalDivider(color = OnboardingGlassTokens.TextTertiary)

                Text(
                    text = stringResource(R.string.total_water_liters_per_day_str, summaryUiModel.waterRecommendation.waterLiters),
                    style = MaterialTheme.typography.titleMedium,
                    color = OnboardingGlassTokens.TextPrimary
                )
            }
        }

        BaseBlock(
            title = stringResource(R.string.daily_macros_str),
            subtitle = stringResource(R.string.daily_macros_subtitle_str),
            supporting = "supporting",
            leadingIcon = painterResource(R.drawable.chemistry_svgrepo_com),
            isIconColorful = true,
            iconAmbient = Color.Green,
            iconSpot = Color.Green.copy(alpha = 0.3f)
        ) {

            MacroTargetsChart(
                proteinGrams = summaryUiModel.macros.proteinGrams.toFloat(),
                fatGrams = summaryUiModel.macros.fatGrams.toFloat(),
                carbsGrams = summaryUiModel.macros.carbGrams.toFloat()
            )
        }
    }
}

@Composable
fun SummaryMetricRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = OnboardingGlassTokens.TextSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = OnboardingGlassTokens.TextPrimary
        )
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "ru"
)
@Composable
private fun SummaryStepPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        SummaryStep(
            draft = UserDraft(
                sex = Sex.MALE,
                weightKg = 60.0,
                heightCm = 180.0,
                activityLevel = ActivityLevel.MODERATE,
                goal = Goal.MAINTAIN
            ),
            summaryUiModel = SummaryUiModel(
                bmi = 23.2f,
                bmiLabel = "",
                dailyCalories = 2200,
                bmr = 23,
                activityMultiplier = 15f,
                goalAdjustmentPercent = 5,
                macros = MacroTargets(
                    proteinGrams = 160.0,
                    fatGrams = 70.0,
                    carbGrams = 240.0
                ),
                waterRecommendation = DailyWaterRecommendation(
                    waterLiters = 2.3,
                    waterMl = 2300.0,
                    baseAmount = 2.0,
                    activityAdjustment = 1.5,
                    goalAdjustment = 1.0,
                    ageAdjustment = 1.0,
                    sexAdjustment = 1.0
                )
            )
        )
    }
}
