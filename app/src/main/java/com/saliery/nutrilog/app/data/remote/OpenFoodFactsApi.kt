package com.saliery.nutrilog.app.data.remote

import com.saliery.nutrilog.app.data.remote.dto.BarcodeResponseDto
import com.saliery.nutrilog.app.data.remote.dto.GetNutriscoreDataResponse
import com.saliery.nutrilog.app.data.remote.dto.SearchResponseDto
import retrofit2.http.GET

import retrofit2.http.Path
import retrofit2.http.Query

interface OpenFoodFactsApi {

    @GET("api/v2/search")
    suspend fun searchProducts(
        @Query("search_terms") query: String,
        @Query("categories_tags_en") categoryTag: String? = null,
        @Query("fields") fields: String = "product_name,code,brands,nutriments,selected_images",
        @Query("page_size") pageSize: Int = 15
    ): SearchResponseDto

    @GET("api/v2/product/{barcode}")
    suspend fun getProductByBarcode(
        @Path("barcode") barcode: String,
        @Query("fields") fields: String =
            "product_name,brands,food_groups,nutrition_grades," +
                    "allergens,nutriments,selected_images,nutriscore_data," +
                    "nova_group,traces,additives_tags,ingredients,last_edit_dates_tags," +
                    "product_quantity,product_quantity_unit,quantity,created_t,last_editor,last_updated_t"
    ): BarcodeResponseDto
}