package com.alongo.manyweather.di

import com.alongo.manyweather.data.networking.WeatherAPI
import com.alongo.manyweather.data.repository.AuthRepositoryImpl
import com.alongo.manyweather.data.repository.WeatherRepositoryImpl
import com.alongo.manyweather.domain.repository.AuthRepository
import com.alongo.manyweather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherAPI: WeatherAPI): WeatherRepository {
        return WeatherRepositoryImpl(weatherAPI)
    }

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherAPI {
        return retrofit.create(WeatherAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl()
}