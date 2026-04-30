package com.saliery.nutrilog.app.presentation.mealEntryScreen.components

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.presentation.common.GlassCard
import com.saliery.nutrilog.app.presentation.helper.toFormattedTime
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalTime
import network.chaintech.kmp_date_time_picker.ui.timepicker.WheelTimePickerDialog
import java.time.LocalTime

@Composable
fun MealTimeSection(
    selectedTime: LocalTime,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    onTimeChanged: (LocalTime) -> Unit
) {
    var showPicker by remember { mutableStateOf(false) }

    GlassCard(
        modifier = modifier.fillMaxWidth(),
        hazeState = hazeState,
        contentPadding = PaddingValues(18.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Время",
                style = MaterialTheme.typography.titleMedium,
                color = OnboardingGlassTokens.TextPrimary
            )

            Surface(
                onClick = { showPicker = true },
                shape = RoundedCornerShape(18.dp),
                color = OnboardingGlassTokens.GlassSurface,
                border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = selectedTime.toFormattedTime(),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = OnboardingGlassTokens.TextPrimary
                        )

                        Text(
                            text = "Нажмите, чтобы изменить",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnboardingGlassTokens.TextTertiary
                        )
                    }

                    Icon(
                        painter = painterResource(R.drawable.time_past_svgrepo_com),
                        contentDescription = null,
                        tint = OnboardingGlassTokens.Primary,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }

    if (showPicker) {
        WheelTimePickerDialog(
            height = 200.dp,
            title = "Выберите время",
            doneLabel = "Готово",
            titleStyle = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = OnboardingGlassTokens.TextPrimary
            ),
            doneLabelStyle = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = OnboardingGlassTokens.Primary
            ),
            selectedTextStyle = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Black,
                color = OnboardingGlassTokens.Primary
            ),
            defaultTextStyle = MaterialTheme.typography.bodyLarge.copy(
                color = OnboardingGlassTokens.TextTertiary
            ),
            containerColor = OnboardingGlassTokens.GlassSurfaceStrong.copy(alpha = 0.96f),
            shape = RoundedCornerShape(32.dp),
            showTimePicker = true,
            startTime = selectedTime.toKotlinLocalTime(),
            onDoneClick = { pickedTime ->
                showPicker = false
                onTimeChanged(pickedTime.toJavaLocalTime())
            },
            onDismiss = { showPicker = false },
            modifier = Modifier
                .then(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        Modifier.hazeEffect(
                            state = hazeState,
                            style = HazeStyle(
                                blurRadius = 30.dp, tint = HazeTint(
                                    OnboardingGlassTokens.GlassSurface.copy(alpha = 0.8f)
                                )
                            )
                        )
                    } else Modifier
                )
        )
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun MealTimeSectionPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        MealTimeSection(
            selectedTime = LocalTime.now(),
            hazeState = rememberHazeState(),
            onTimeChanged = {},
        )
    }
}