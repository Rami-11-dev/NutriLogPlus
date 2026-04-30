package com.saliery.nutrilog.app

import android.app.Application
import com.saliery.nutrilog.BuildConfig
import com.saliery.nutrilog.app.di.coilModule
import com.saliery.nutrilog.app.di.dataSourceModule
import com.saliery.nutrilog.app.di.dataStoreModule
import com.saliery.nutrilog.app.di.networkModule
import com.saliery.nutrilog.app.di.reducerModule
import com.saliery.nutrilog.app.di.repositoryModule
import com.saliery.nutrilog.app.di.roomModule
import com.saliery.nutrilog.app.di.useCaseModule
import com.saliery.nutrilog.app.di.viewModelModule
import com.saliery.nutrilog.src.DebugTree
import com.saliery.nutrilog.src.ReleaseTree
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class NutriLogApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        startKoin {
            androidContext(this@NutriLogApp)
            modules(
                reducerModule,
                viewModelModule,
                useCaseModule,
                networkModule,
                dataSourceModule,
                dataStoreModule,
                repositoryModule,
                roomModule,
                coilModule,
            )
        }
    }
}