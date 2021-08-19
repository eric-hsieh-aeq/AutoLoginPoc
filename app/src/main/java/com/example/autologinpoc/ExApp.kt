package com.example.autologinpoc

import android.app.Application
import timber.log.Timber

class ExApp : Application(){
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}