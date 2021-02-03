package com.booking.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

    }


    companion object {
        lateinit var instance: MyApp
            private set
    }



}


