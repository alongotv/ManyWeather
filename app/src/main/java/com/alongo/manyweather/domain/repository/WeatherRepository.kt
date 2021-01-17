package com.alongo.manyweather.domain.repository

import com.alongo.manyweather.data.model.entity.weather.Weather
import com.alongo.manyweather.data.model.networking.WeatherByCityNameRequest
import com.alongo.manyweather.data.model.networking.WeatherByCoordinatesRequest
import io.reactivex.Single

interface WeatherRepository {

    fun getWeatherByCityName(request: WeatherByCityNameRequest): Single<Weather>

    fun getWeatherByCoordinates(request: WeatherByCoordinatesRequest): Single<Weather>

}