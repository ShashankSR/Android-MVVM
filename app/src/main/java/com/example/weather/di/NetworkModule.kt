package com.example.weather.di

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class NetworkModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideOkHttpBase(): OkHttpClient {
            return OkHttpClient.Builder().build()
        }

        @Provides
        @JvmStatic
        fun providesGsonFactory(): Converter.Factory {
            return GsonConverterFactory.create()
        }

        @Provides
        @JvmStatic
        fun providesCallAdapterFactory(): CallAdapter.Factory {
            return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
        }

        @Provides
        @Singleton
        @JvmStatic
        fun providePlusRetrofitClient(
            client: OkHttpClient,
            converterFactory: Converter.Factory,
            callAdapterFactory: CallAdapter.Factory,
            @Named("baseUrl") baseUrl: String
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(client)
                .build()
        }
    }
}
