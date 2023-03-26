package com.example.wasim

import android.app.Application
import com.example.wasim.BuildConfig.DEBUG
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
