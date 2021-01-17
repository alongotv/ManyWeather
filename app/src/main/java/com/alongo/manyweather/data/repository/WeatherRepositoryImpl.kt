package com.alongo.manyweather.data.repository

import com.alongo.manyweather.data.model.entity.weather.Weather
import com.alongo.manyweather.data.model.networking.WeatherByCityNameRequest
import com.alongo.manyweather.data.model.networking.WeatherByCoordinatesRequest
import com.alongo.manyweather.data.networking.WeatherAPI
import com.alongo.manyweather.domain.repository.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(val weatherApi: WeatherAPI) : WeatherRepository {

    override fun getWeatherByCityName(request: WeatherByCityNameRequest): Single<Weather> {
        return weatherApi.getWeatherByCityName(
            request.openWeatherToken, request.cityName, request.measurementUnit ?: "metric"
        )
    }


    override fun getWeatherByCoordinates(request: WeatherByCoordinatesRequest): Single<Weather> {
        return weatherApi.getWeatherByCoordinates(request.openWeatherToken ,request.latitude, request.longitude)
    }

}