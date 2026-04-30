package com.saliery.nutrilog.app.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saliery.nutrilog.app.data.local.room.converters.CookingMethodConverter
import com.saliery.nutrilog.app.data.local.room.converters.DataSourceTypeConverter
import com.saliery.nutrilog.app.data.local.room.converters.ImageTypeConverter
import com.saliery.nutrilog.app.data.local.room.converters.ListStringConverter
import com.saliery.nutrilog.app.data.local.room.converters.LocalDateTimeConverter
import com.saliery.nutrilog.app.data.local.room.converters.MealTypeConverter
import com.saliery.nutrilog.app.data.local.room.converters.NutritionBasisConverter
import com.saliery.nutrilog.app.data.local.room.dao.MealEntryDao
import com.saliery.nutrilog.app.data.local.room.dao.MealIngredientDao
import com.saliery.nutrilog.app.data.local.room.dao.MealItemDao
import com.saliery.nutrilog.app.data.local.room.dao.ProductDao
import com.saliery.nutrilog.app.data.local.room.dao.RecipeDao
import com.saliery.nutrilog.app.data.local.room.dao.RecipeIngredientDao
import com.saliery.nutrilog.app.data.local.room.dao.WeightEntryDao
import com.saliery.nutrilog.app.data.local.room.entity.entries.MealEntryEntity
import com.saliery.nutrilog.app.data.local.room.entity.entries.MealItemEntity
import com.saliery.nutrilog.app.data.local.room.entity.entries.MealItemIngredientEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductImageEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductMicronutrientEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductNutriscoreDataEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductPortionEntity
import com.saliery.nutrilog.app.data.local.room.entity.recipe.RecipeEntity
import com.saliery.nutrilog.app.data.local.room.entity.recipe.RecipeIngredientEntity
import com.saliery.nutrilog.app.data.local.room.entity.user.WeightEntryEntity

@Database(
    entities = [
        ProductEntity::class,
        ProductImageEntity::class,
        ProductNutriscoreDataEntity::class,
        ProductMicronutrientEntity::class,
        ProductPortionEntity::class,
        RecipeEntity::class,
        RecipeIngredientEntity::class,
        MealEntryEntity::class,
        MealItemEntity::class,
        MealItemIngredientEntity::class,
        WeightEntryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    LocalDateTimeConverter::class,
    CookingMethodConverter::class,
    MealTypeConverter::class,
    ListStringConverter::class,
    DataSourceTypeConverter::class,
    NutritionBasisConverter::class,
    ImageTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    abstract fun mealEntryDao(): MealEntryDao
    abstract fun mealIngredientDao(): MealIngredientDao
    abstract fun mealItemDao(): MealItemDao

    abstract fun recipeDao(): RecipeDao
    abstract fun recipeIngredientDao(): RecipeIngredientDao

    abstract fun weightEntryDao(): WeightEntryDao
}