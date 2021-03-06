package com.alongo.manyweather.data.model.entity.weather

data class WeatherInternalParameter(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)
