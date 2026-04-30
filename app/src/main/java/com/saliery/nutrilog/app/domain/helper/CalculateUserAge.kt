package com.saliery.nutrilog.app.domain.helper

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.Period

fun calculateAgeFromLocalDate(birthDate: LocalDate): Int {

    return Period.between(birthDate.toJavaLocalDate(), java.time.LocalDate.now())
        .years
        .coerceAtLeast(0)
}