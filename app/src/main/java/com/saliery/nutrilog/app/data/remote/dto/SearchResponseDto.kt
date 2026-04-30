package com.saliery.nutrilog.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(
    val count: Int?,
    val page: Int?,
    @SerializedName("page_count")
    val pageCount: Int?,
    @SerializedName("page_size")
    val pageSize: Int?,
    val products: List<ProductListItemDto>?
)

data class ProductListItemDto(
    val code: String?,
    @SerializedName("product_name")
    val productName: String?,
    val brands: String?,
    @SerializedName("nutrition_grades")
    val nutritionGrades: String?,
    @SerializedName("food_groups")
    val foodGroup: String?,
    val nutriments: NutrimentsDto?,
    @SerializedName("selected_images")
    val selectedImages: SelectedImagesDto?,
)
