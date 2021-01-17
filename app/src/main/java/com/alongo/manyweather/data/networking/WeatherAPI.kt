package com.alongo.manyweather.data.networking

import com.alongo.manyweather.data.model.entity.weather.Weather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherAPI {

    @Headers("Content-Type: application/json")
    @GET("weather")
    fun getWeatherByCityName(
        @Query("appid") openWeatherToken: String,
        @Query("q") cityName: String,
        @Query("units") measurementUnit: String = "metric"
    ): Single<Weather>

    @Headers("Content-Type: application/json")
    @GET("weather")
    fun getWeatherByCoordinates(
        @Query("appid") openWeatherToken: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") measurementUnit: String = "metric"
    ): Single<Weather>
}