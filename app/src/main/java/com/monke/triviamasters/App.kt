package com.monke.triviamasters

import android.app.Application
import com.monke.triviamasters.di.components.DaggerAppComponent

class App: Application() {

    val appComponent = DaggerAppComponent.builder().context(this).build()


    override fun onCreate() {
        super.onCreate()
    }
}