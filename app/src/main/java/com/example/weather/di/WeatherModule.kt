package com.example.weather.di

import androidx.lifecycle.ViewModel
import com.example.weather.data.WeatherApi
import com.example.weather.utils.ViewModelKey
import com.example.weather.view.MainActivity
import com.example.weather.viewmodel.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
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

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun providesWeatherViewModel(weatherViewModel: WeatherViewModel): ViewModel
}
