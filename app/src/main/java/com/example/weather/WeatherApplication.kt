package com.example.weather

import android.app.Application
import com.example.weather.di.DaggerAppComponent

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build().inject(this)
    }
}
