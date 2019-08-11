package com.example.weather

import android.app.Activity
import android.app.Application
import com.example.weather.di.AppComponent
import com.example.weather.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

open class WeatherApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    private lateinit var applicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        getComponent().inject(this)
    }

    open fun getComponent(): AppComponent {
        if (!this::applicationComponent.isInitialized) {
            applicationComponent = DaggerAppComponent.builder()
                .application(this)
                .baseUrl("https://api.apixu.com")
                .build()
        }
        return applicationComponent
    }
}
