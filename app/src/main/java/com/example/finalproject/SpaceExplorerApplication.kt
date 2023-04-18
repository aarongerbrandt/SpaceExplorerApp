package com.example.finalproject

import android.app.Application

class SpaceExplorerApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        ApodRepository.initialize(this)
        RoverRepository.initialize(this)
    }
}