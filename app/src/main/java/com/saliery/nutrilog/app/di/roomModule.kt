package com.saliery.nutrilog.app.di

import androidx.room.Room
import com.saliery.nutrilog.app.data.local.room.AppDatabase
import com.saliery.nutrilog.app.data.local.room.dao.MealEntryDao
import com.saliery.nutrilog.app.data.local.room.dao.MealIngredientDao
import com.saliery.nutrilog.app.data.local.room.dao.MealItemDao
import com.saliery.nutrilog.app.data.local.room.dao.ProductDao
import com.saliery.nutrilog.app.data.local.room.dao.RecipeDao
import com.saliery.nutrilog.app.data.local.room.dao.RecipeIngredientDao
import com.saliery.nutrilog.app.data.local.room.dao.WeightEntryDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "nutrilog_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    single<ProductDao> { get<AppDatabase>().productDao() }

    single<MealEntryDao> { get<AppDatabase>().mealEntryDao() }
    single<MealItemDao> { get<AppDatabase>().mealItemDao() }
    single<MealIngredientDao> { get<AppDatabase>().mealIngredientDao() }

    single<RecipeDao> { get<AppDatabase>().recipeDao() }
    single<RecipeIngredientDao> { get<AppDatabase>().recipeIngredientDao() }

    single<WeightEntryDao> { get<AppDatabase>().weightEntryDao() }
}