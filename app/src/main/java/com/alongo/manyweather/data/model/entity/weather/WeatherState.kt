package com.alongo.manyweather.data.model.entity.weather

import com.google.gson.annotations.SerializedName

data class WeatherState (
    val id: Int,
    @SerializedName("main")
    val title: String,
    val description: String,
    @SerializedName("icon")
    val iconName: String //	Should be used to retrieve the image from the backend. Template: https://openweathermap.org/img/wn/{iconName}.png
)