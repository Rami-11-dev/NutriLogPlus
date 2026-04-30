package com.saliery.nutrilog.app.di

import com.saliery.nutrilog.app.data.datasource.ProductDataSource
import com.saliery.nutrilog.app.data.local.preferences.UserLocalDataSource
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
import com.saliery.nutrilog.app.data.repository.WeightRepositoryImpl
import com.saliery.nutrilog.app.domain.repository.MealEntryRepository
import com.saliery.nutrilog.app.domain.repository.ProductRepository
import com.saliery.nutrilog.app.domain.repository.RecipeRepository
import com.saliery.nutrilog.app.domain.repository.UserRepository
import com.saliery.nutrilog.app.domain.repository.WeightRepository
import com.saliery.nutrilog.app.domain.usecase.SaveWeightUseCase
import org.koin.dsl.module

val dataModule = module {

}