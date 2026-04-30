package com.saliery.nutrilog.app.presentation.common

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.saliery.nutrilog.R
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import kotlinx.datetime.LocalDate
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import network.chaintech.kmp_date_time_picker.utils.now

@Composable
fun GlassWheelDatePicker(
    showDatePicker: Boolean,
    hazeState: HazeState? = null,
    onDismiss: () -> Unit,
    onDoneClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    if (showDatePicker) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .then(
                        if (hazeState != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            Modifier.hazeEffect(
                                state = hazeState,
                                style = HazeStyle(
                                    blurRadius = 20.dp,
                                    tint = HazeTint(OnboardingGlassTokens.GlassSurfaceStrong.copy(alpha = 0.8f))
                                )
                            )
                        } else {
                            Modifier
                        }
                    ),
                shape = RoundedCornerShape(28.dp),
                color = OnboardingGlassTokens.GlassSurface.copy(alpha = 0.5f),
                border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.select_date_str),
                        style = MaterialTheme.typography.titleMedium,
                        color = OnboardingGlassTokens.TextPrimary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    WheelDatePickerView(
                        modifier = Modifier.fillMaxWidth(),
                        showDatePicker = true,
                        containerColor = Color.Transparent,
                        rowCount = 3,
                        height = 180.dp,
                        yearsRange = 1950..LocalDate.now().year,

                        selectedDateTextStyle = MaterialTheme.typography.titleLarge.copy(
                            color = OnboardingGlassTokens.Primary,
                            fontWeight = FontWeight.Bold
                        ),
                        defaultDateTextStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = OnboardingGlassTokens.TextSecondary
                        ),

                        selectorProperties = WheelPickerDefaults.selectorProperties(
                            borderColor = OnboardingGlassTokens.Primary.copy(alpha = 0.3f)
                        ),

                        onDoneClick = { onDoneClick(it) },
                        onDismiss = onDismiss,
                        doneLabelStyle = MaterialTheme.typography.labelLarge.copy(
                            color = OnboardingGlassTokens.Primary,
                            fontWeight = FontWeight.ExtraBold
                        ),
                        showShortMonths = false,
                        showMonthAsNumber = false,
                        dateTimePickerView = DateTimePickerView.DIALOG_VIEW
                    )
                }
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE,
    showBackground = true,

)
@Composable
private fun GlassWheelDatePickerPreview() {
    NutriLogTheme(
        darkTheme = true
    ) {
        GlassWheelDatePicker(
            showDatePicker = true,
            onDismiss = {},
            onDoneClick = {}
        )
    }
}