package com.example.mycatapp

import android.app.Application
import com.example.mycatapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyCatApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            // Provide Android context
            androidContext(this@MyCatApplication)
            // Load all our defined singletons and ViewModels!
            modules(appModule)
        }
    }
}
