package com.saliery.nutrilog.mapperTest

import com.saliery.nutrilog.app.data.remote.dto.ComponentsDto
import com.saliery.nutrilog.app.data.remote.dto.GetNutriscoreDataResponse
import com.saliery.nutrilog.app.data.remote.dto.NutriscoreDataDto
import com.saliery.nutrilog.app.data.remote.dto.NutriscoreDataProduct
import com.saliery.nutrilog.app.data.remote.dto.SingleComponentDto
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class NutriscoreDataDtoMapperTest {

    @Test
    fun toDomainShouldMapCorrectlyWhenAllRequiredFieldsArePresent(){
        // GIVEN
        val dto = GetNutriscoreDataResponse(
            code = "3017624010701",
            product = NutriscoreDataProduct(
                nutriscore_data = NutriscoreDataDto(
                    components = ComponentsDto(
                        negative = listOf(
                            SingleComponentDto(
                                id = "energy",
                                points = 6,
                                points_max = 10,
                                unit = "kJ",
                                value = 2225
                            ),
                            SingleComponentDto(
                                id = "sugars",
                                points = 15,
                                points_max = 15,
                                unit = "g",
                                value = 56.3
                            ),
                            SingleComponentDto(
                                id = "saturated_fat",
                                points = 10,
                                points_max = 10,
                                unit = "g",
                                value = 10.6
                            ),
                            SingleComponentDto(
                                id = "salt",
                                points = 0,
                                points_max = 20,
                                unit = "g",
                                value = 0.11
                            )
                        ),
                        positive = listOf(
                            SingleComponentDto(
                                id = "fiber",
                                points = 0,
                                points_max = 5,
                                unit = "g",
                                value = null
                            ),
                            SingleComponentDto(
                                id = "fruits_vegetables_legumes",
                                points = 0,
                                points_max = 5,
                                unit = "%",
                                value = 0
                            )
                        )
                    )
                )
            ),
            status = 1,
            status_verbose = "product found"
        )

        // WHEN
        val result = dto
        val negativeSize = result.product?.nutriscore_data?.components?.negative?.size ?: 0
        val positiveSize = result.product?.nutriscore_data?.components?.positive?.size ?: 0

        // THEN
        assertNotNull(result)
        assertEquals("3017624010701", result.code)
        assertEquals(4, negativeSize)
        assertEquals(2, positiveSize)
    }
}