package com.example.weather

import com.example.weather.di.AppComponent
import com.example.weather.di.DaggerAppComponent

class WeatherTestApplication : WeatherApplication() {

    override fun getComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .application(this)
            .baseUrl("http://localhost:8080/")
            .build()
    }
}
