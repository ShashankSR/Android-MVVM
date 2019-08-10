package com.example.weather.di

import androidx.lifecycle.ViewModelProvider
import com.example.weather.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}