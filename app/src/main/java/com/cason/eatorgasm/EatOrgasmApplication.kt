package com.cason.eatorgasm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EatOrgasmApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}