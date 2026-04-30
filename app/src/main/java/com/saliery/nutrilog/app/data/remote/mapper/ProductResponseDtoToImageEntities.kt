package com.saliery.nutrilog.app.data.remote.mapper

import com.saliery.nutrilog.app.data.local.room.entity.product.ProductImageEntity
import com.saliery.nutrilog.app.data.remote.dto.BarcodeResponseDto
import com.saliery.nutrilog.app.data.remote.dto.ImageDetailsDto
import com.saliery.nutrilog.app.domain.model.product.ImageType
import java.util.Locale

fun BarcodeResponseDto.toProductImageEntities(): List<ProductImageEntity> {

    val entities = mutableListOf<ProductImageEntity>()
    val barcode = product?.code.orEmpty()

    product?.selected_images?.let { images ->

        images.front?.let {
            entities.add(createImageEntity(barcode, ImageType.FRONT, it))
        }
        images.ingredients?.let {
            entities.add(createImageEntity(barcode, ImageType.INGREDIENTS, it))
        }
        images.nutrition?.let {
            entities.add(createImageEntity(barcode, ImageType.NUTRITION, it))
        }
        images.packaging?.let {
            entities.add(createImageEntity(barcode, ImageType.PACKAGING, it))
        }
    }

    return entities
}

private fun createImageEntity(
    productId: String,
    imageType: ImageType,
    imageDetails: ImageDetailsDto,
    currentLang: String = Locale.getDefault().language
): ProductImageEntity {
    val displayUrl = imageDetails.display?.get(currentLang)
        ?: imageDetails.display?.values?.firstOrNull()

    val smallUrl = imageDetails.small?.get(currentLang)
        ?: imageDetails.small?.values?.firstOrNull()

    val thumbUrl = imageDetails.thumb?.get(currentLang)
        ?: imageDetails.thumb?.values?.firstOrNull()

    val language = imageDetails.display?.keys?.firstOrNull { it == currentLang }
        ?: imageDetails.display?.keys?.firstOrNull()

    return ProductImageEntity(
        productId = productId,
        imageType = imageType,
        displayUrl = displayUrl,
        smallUrl = smallUrl,
        thumbUrl = thumbUrl,
        language = language,
        thumbCached = false,
    )
}