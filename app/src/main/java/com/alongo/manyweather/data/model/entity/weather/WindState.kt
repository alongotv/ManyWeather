package com.alongo.manyweather.data.model.entity.weather

import com.google.gson.annotations.SerializedName

data class WindState(val speed: Double, @SerializedName("deg") val degrees: Int)