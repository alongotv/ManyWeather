package com.alongo.manyweather.data.model.networking

data class WeatherByCityNameRequest(
    val openWeatherToken: String,
    val cityName: String,
    val measurementUnit: String? = "metric"
)