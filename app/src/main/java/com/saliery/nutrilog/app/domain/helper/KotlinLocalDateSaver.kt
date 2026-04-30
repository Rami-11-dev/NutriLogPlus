package com.saliery.nutrilog.app.domain.helper

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import kotlinx.datetime.LocalDate

val KotlinLocalDateSaver = object : Saver<LocalDate, String> {
    override fun SaverScope.save(value: LocalDate): String {
        return value.toString()
    }

    override fun restore(value: String): LocalDate? {
        return LocalDate.parse(value)
    }
}