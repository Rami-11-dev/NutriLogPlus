package com.saliery.nutrilog.app.domain.helper

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import java.time.LocalDate

val JavaLocalDateSaver = object : Saver<LocalDate, Long> {
    override fun SaverScope.save(value: LocalDate): Long {
        return value.toEpochDay()
    }

    override fun restore(value: Long): LocalDate {
        return LocalDate.ofEpochDay(value)
    }
}