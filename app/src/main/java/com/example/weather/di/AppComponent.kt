package com.example.weather.di

import com.example.weather.WeatherApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, NetworkModule::class, ViewModelModule::class, WeatherModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: WeatherApplication): Builder

        @BindsInstance
        fun baseUrl(@Named("baseUrl") baseUrl: String): Builder
    }

    fun inject(application: WeatherApplication)
}
