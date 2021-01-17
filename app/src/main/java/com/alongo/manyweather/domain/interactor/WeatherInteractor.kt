package com.alongo.manyweather.domain.interactor

import com.alongo.manyweather.data.model.entity.weather.Weather
import com.alongo.manyweather.data.model.networking.WeatherByCityNameRequest
import com.alongo.manyweather.data.model.networking.WeatherByCoordinatesRequest
import com.alongo.manyweather.domain.repository.AuthRepository
import com.alongo.manyweather.domain.repository.WeatherRepository
import io.reactivex.Single


class WeatherInteractor(
    private val weatherRepository: WeatherRepository,
    private val authRepository: AuthRepository
) {

    fun getWeatherByCityName(name: String): Single<Weather> {
        val token = authRepository.getWeatherToken()
        return weatherRepository.getWeatherByCityName(WeatherByCityNameRequest(token, name))
    }

    fun getWeatherByGeolocation(latitude: Double, longitude: Double): Single<Weather> {
        val token = authRepository.getWeatherToken()
        return weatherRepository.getWeatherByCoordinates(
            WeatherByCoordinatesRequest(
                token,
                latitude,
                longitude
            )
        )
    }
}