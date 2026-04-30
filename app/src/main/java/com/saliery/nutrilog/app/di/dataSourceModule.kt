package com.saliery.nutrilog.app.di

import com.saliery.nutrilog.app.data.datasource.ProductDataSource
import com.saliery.nutrilog.app.data.datasource.ProductDataSourceImpl
import com.saliery.nutrilog.app.data.local.preferences.UserLocalDataSource
import com.saliery.nutrilog.app.data.local.preferences.UserPreferencesManager
import com.saliery.nutrilog.app.data.local.preferences.water.UserWaterProgressDataSource
import com.saliery.nutrilog.app.data.local.preferences.water.UserWaterProgressDataSourceImpl
import com.saliery.nutrilog.app.data.local.room.dao.ProductDao
import com.saliery.nutrilog.app.data.remote.OpenFoodFactsApi
import org.koin.dsl.module

val dataSourceModule = module {

    single<ProductDataSource> {
        ProductDataSourceImpl(
            productDao = get<ProductDao>(),
            api = get<OpenFoodFactsApi>()
        )
    }

    single<UserWaterProgressDataSource> {
        UserWaterProgressDataSourceImpl(
            context = get(),
            json = get()
        )
    }

    single<UserLocalDataSource> {
        UserPreferencesManager(
            context = get(),
            json = get()
        )
    }
}