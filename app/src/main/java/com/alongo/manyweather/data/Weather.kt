package com.alongo.manyweather.data

import com.google.gson.annotations.SerializedName

data class Weather(
    val name: String,
    @SerializedName("coord") val coordinate: Coordinate,
    @SerializedName("main") val weatherData: WeatherData,
    @SerializedName("sys") val weatherInternalParameter: WeatherInternalParameter
)