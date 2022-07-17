package com.hieuwu.justart

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JustArtApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}