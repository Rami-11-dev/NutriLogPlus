package com.saliery.nutrilog.mapperTest

import com.saliery.nutrilog.app.data.remote.dto.ProductListItemDto
import com.saliery.nutrilog.app.data.remote.dto.SearchResponseDto
import com.saliery.nutrilog.app.data.remote.mapper.toEntities
import org.junit.Test
import kotlin.test.assertEquals

class SearchResponseToProductPreviewModelMapperTest {

    @Test
    fun toPreviewShouldCorrectlyProcessEveryListItem() {
        // GIVEN
        val searchResponse = SearchResponseDto(
            count = 400,
            page = 1,
            pageCount = 1,
            pageSize = 25,
            products = listOf<ProductListItemDto>(
                ProductListItemDto(
                    code = "6111242107366",
                    brands = "Jaouda",
                    productName = "Press Up moye",
                    nutritionGrades = "a",
                    foodGroup = "other",
                    nutriments = null,
                    selectedImages = null
                ),
                ProductListItemDto(
                    code = "6111035002052",
                    productName = "Bul'tropicale",
                    brands = "Les Eaux Minérales d'Oulmès, Oulmès",
                    nutritionGrades = "a",
                    foodGroup = "other",
                    nutriments = null,
                    selectedImages = null
                ),
                ProductListItemDto(
                    code = "5060490010496",
                    productName = "GINGER SHOTS",
                    brands = "MOJU",
                    nutritionGrades = "a",
                    foodGroup = "other",
                    nutriments = null,
                    selectedImages = null
                ),
                ProductListItemDto(
                    code = "4056489217435",
                    productName = "Apple, Mango, Spinach & Avocado Smoothie",
                    brands = "Chef Select",
                    nutritionGrades = "a",
                    foodGroup = "other",
                    nutriments = null,
                    selectedImages = null
                ),
                ProductListItemDto(
                    code = "5942204005779",
                    productName = "Pepsi Twist 2l",
                    brands = "Pepsi",
                    nutritionGrades = "a",
                    foodGroup = "other",
                    nutriments = null,
                    selectedImages = null
                ),
                ProductListItemDto(
                    code = "6111179006176",
                    productName = "Pulpe orange",
                    brands = null,
                    nutritionGrades = "a",
                    foodGroup = "other",
                    nutriments = null,
                    selectedImages = null
                ),
                ProductListItemDto(
                    code = "4056489793977",
                    productName = null,
                    brands = "Lidl",
                    nutritionGrades = "a",
                    foodGroup = "other",
                    nutriments = null,
                    selectedImages = null
                ),
                ProductListItemDto(
                    code = null,
                    productName = "Orange",
                    brands = "Cool brand",
                    nutritionGrades = "a",
                    foodGroup = "other",
                    nutriments = null,
                    selectedImages = null
                ),
                ProductListItemDto(
                    code = "1234567890",
                    productName = null,
                    brands = null,
                    nutritionGrades = null,
                    foodGroup = null,
                    nutriments = null,
                    selectedImages = null
                )
            )
        )

        // WHEN
        val results = searchResponse.toEntities()

        // THEN
        assertEquals(7, results.size)
    }
}