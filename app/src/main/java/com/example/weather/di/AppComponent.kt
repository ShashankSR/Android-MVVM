package com.example.weather.di

import com.example.weather.WeatherApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: WeatherApplication): Builder
    }

    fun inject(application: WeatherApplication)
}
