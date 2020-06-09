package com.example.kangraemin.base

import android.app.Application
import com.example.local.di.localDataModule
import com.example.data.di.repositoryModule
import com.example.kangraemin.BuildConfig
import com.example.kangraemin.module.*
import com.example.data.di.networkUtilModule
import com.example.remote.di.remoteDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KangBaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            } else {
                androidLogger(Level.NONE)
            }
            androidContext(this@KangBaseApplication)
            modules(
                localDataModule,
                networkUtilModule,
                remoteDataModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}