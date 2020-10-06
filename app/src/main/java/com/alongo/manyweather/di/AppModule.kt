package com.alongo.manyweather.di

import com.alongo.manyweather.remote.WeatherAPI
import com.alongo.manyweather.utilities.WEATHER_API_ENDPOINT
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val gsonInstance = GsonBuilder().run {
            setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            create()
        }

        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gsonInstance))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(WEATHER_API_ENDPOINT)
            .build()!!
    }

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherAPI {
        return retrofit.create(WeatherAPI::class.java)
    }
}