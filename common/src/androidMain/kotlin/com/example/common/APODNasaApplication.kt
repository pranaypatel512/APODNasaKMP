package com.example.common

import android.app.Application
import com.example.common.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class APODNasaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(level = if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(androidContext = this@APODNasaApplication)
        }
    }
}
