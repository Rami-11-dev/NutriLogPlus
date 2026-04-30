package com.saliery.nutrilog.app.domain.model.user

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import network.chaintech.kmp_date_time_picker.utils.now

@Serializable
data class DailyWaterProgress(
    val currentProgressMl: Double = 0.0,
    val currentDate: LocalDate = LocalDate.now()
)
