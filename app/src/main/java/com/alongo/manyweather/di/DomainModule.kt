package com.alongo.manyweather.di

import com.alongo.manyweather.domain.interactor.WeatherInteractor
import com.alongo.manyweather.domain.repository.AuthRepository
import com.alongo.manyweather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideWeatherInteractor(
        weatherRepository: WeatherRepository,
        authRepository: AuthRepository
    ): WeatherInteractor =
        WeatherInteractor(weatherRepository, authRepository)
}