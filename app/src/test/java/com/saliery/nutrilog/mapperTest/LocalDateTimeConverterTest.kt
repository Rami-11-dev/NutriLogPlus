package com.saliery.nutrilog.mapperTest

import com.saliery.nutrilog.app.data.local.room.converters.LocalDateTimeConverter
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class LocalDateTimeConverterTest {

    private lateinit var converter: LocalDateTimeConverter

    @Before
    fun setup() {
        converter = LocalDateTimeConverter()
    }

    @Test
    fun shouldConvertCorrectlyToStringAndBack() {
        val testLocalDatetime = LocalDateTime.now()

        val resultTo = converter.fromLocalDateTimeToString(testLocalDatetime)
        println("LocalDateTime to String: $resultTo")

        Assert.assertNotNull(resultTo)

        val resultFrom = converter.toLocalDateTimeFromString(resultTo)

        Assert.assertNotNull(resultFrom)

        Assert.assertEquals(testLocalDatetime, resultFrom)
    }
}