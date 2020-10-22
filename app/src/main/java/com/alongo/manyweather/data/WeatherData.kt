package com.alongo.manyweather.data

import com.google.gson.annotations.SerializedName

data class WeatherData (
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("feels_like")
    val realFeelTemperature: Double,
    @SerializedName("temp_min")
    val temp_min: Double?,
    @SerializedName("temp_max")
    val temp_max: Double?,
    val pressure: Int,
    val humidity: Int
)