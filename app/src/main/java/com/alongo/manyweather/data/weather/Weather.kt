package com.alongo.manyweather.data.weather

import com.alongo.manyweather.data.Coordinate
import com.google.gson.annotations.SerializedName

data class Weather(
    val name: String,
    @SerializedName("coord") val coordinate: Coordinate,
    @SerializedName("main") val weatherData: WeatherData,
    @SerializedName("sys") val weatherInternalParameter: WeatherInternalParameter,
    @SerializedName("weather") val weatherStateList: List<WeatherState>,
    @SerializedName("wind") val windState: WindState
    )