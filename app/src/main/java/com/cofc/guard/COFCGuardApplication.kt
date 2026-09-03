package com.cofc.guard

import android.app.Application
import android.content.Context

class COFCGuardApplication : Application() {

    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}
