package com.saliery.nutrilog.app.presentation.helper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.entries.LocalMealType

@Composable
fun LocalMealType.toLabel(): String {
    return when (this) {
        LocalMealType.BREAKFAST -> stringResource(R.string.breakfast_str)
        LocalMealType.LUNCH -> stringResource(R.string.lunch_str)
        LocalMealType.DINNER -> stringResource(R.string.dinner_str)
        LocalMealType.SNACK -> stringResource(R.string.snack_str)
    }
}