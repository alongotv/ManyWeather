package com.alongo.manyweather.data

data class WeatherInternalParameter(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)
