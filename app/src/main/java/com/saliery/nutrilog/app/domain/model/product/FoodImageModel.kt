package com.saliery.nutrilog.app.domain.model.product

data class FoodImageModel(
    val imageType: ImageType,
    val displayUrl: String?,
    val smallUrl: String?,
    val thumbUrl: String?,
    val language: String?,
    val isThumbCached: Boolean = false,
    val thumbCacheKey: String? = null
)

enum class ImageType {
    FRONT, INGREDIENTS, NUTRITION,PACKAGING
}
