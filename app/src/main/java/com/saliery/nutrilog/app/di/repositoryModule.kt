package com.saliery.nutrilog.app.di

import com.saliery.nutrilog.app.data.datasource.ProductDataSource
import com.saliery.nutrilog.app.data.local.preferences.UserLocalDataSource
import com.saliery.nutrilog.app.data.local.preferences.water.UserWaterProgressDataSource
import com.saliery.nutrilog.app.data.local.room.AppDatabase
import com.saliery.nutrilog.app.data.local.room.dao.MealEntryDao
import com.saliery.nutrilog.app.data.local.room.dao.MealIngredientDao
import com.saliery.nutrilog.app.data.local.room.dao.MealItemDao
import com.saliery.nutrilog.app.data.local.room.dao.ProductDao
import com.saliery.nutrilog.app.data.local.room.dao.RecipeDao
import com.saliery.nutrilog.app.data.local.room.dao.RecipeIngredientDao
import com.saliery.nutrilog.app.data.local.room.dao.WeightEntryDao
import com.saliery.nutrilog.app.data.repository.MealEntryRepositoryImpl
import com.saliery.nutrilog.app.data.repository.ProductRepositoryImpl
import com.saliery.nutrilog.app.data.repository.RecipeRepositoryImpl
import com.saliery.nutrilog.app.data.repository.UserRepositoryImpl
import com.saliery.nutrilog.app.data.repository.WaterRepositoryImpl
import com.saliery.nutrilog.app.data.repository.WeightRepositoryImpl
import com.saliery.nutrilog.app.domain.repository.MealEntryRepository
import com.saliery.nutrilog.app.domain.repository.ProductRepository
import com.saliery.nutrilog.app.domain.repository.RecipeRepository
import com.saliery.nutrilog.app.domain.repository.UserRepository
import com.saliery.nutrilog.app.domain.repository.WaterRepository
import com.saliery.nutrilog.app.domain.repository.WeightRepository
import org.koin.core.context.GlobalContext.get
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {

    single<ProductRepository> {
        ProductRepositoryImpl(
            dataSource = get<ProductDataSource>()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(
            localDataSource = get<UserLocalDataSource>()
        )
    }

    single<MealEntryRepository> {
        MealEntryRepositoryImpl(
            mealEntryDao = get<MealEntryDao>(),
            mealIngredientDao = get<MealIngredientDao>(),
            mealItemDao = get<MealItemDao>(),
            productDao = get<ProductDao>(),
            recipeDao = get<RecipeDao>(),
            recipeIngredientDao = get<RecipeIngredientDao>(),
            db = get<AppDatabase>()
        )
    }

    single<RecipeRepository> {
        RecipeRepositoryImpl(
            recipeDao = get<RecipeDao>(),
            recipeIngredientDao = get<RecipeIngredientDao>()
        )
    }

    single<WeightRepository> {
        WeightRepositoryImpl(
            weightEntryDao = get<WeightEntryDao>()
        )
    }

    single<WaterRepository> {
        WaterRepositoryImpl(
            dataSource = get<UserWaterProgressDataSource>()
        )
    }
}