package com.saliery.nutrilog.app.data.datasource

import com.saliery.nutrilog.app.data.local.room.entity.product.ProductEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductImageEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductMicronutrientEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductNutriscoreDataEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductPortionEntity

data class ProductRemoteBundle(
    val product: ProductEntity?,
    val images: List<ProductImageEntity>,
    val nutriscore: List<ProductNutriscoreDataEntity>,
    val portions: List<ProductPortionEntity>,
    val micronutrients: ProductMicronutrientEntity?
)
