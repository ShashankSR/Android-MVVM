package com.example.weather.di

import com.example.weather.data.WeatherApi
import com.example.weather.view.MainActivity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import retrofit2.Retrofit

@Module
abstract class WeatherModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun providesMedicineApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)
    }
}
