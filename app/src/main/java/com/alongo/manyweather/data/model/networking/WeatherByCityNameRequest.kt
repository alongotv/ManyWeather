package com.alongo.manyweather.data.model.networking

data class WeatherByCoordinatesRequest(val openWeatherToken: String,
                                       val latitude: Double,
                                       val longitude: Double)