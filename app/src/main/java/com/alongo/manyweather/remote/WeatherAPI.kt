package com.alongo.manyweather.remote

import com.alongo.manyweather.data.Weather
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherAPI {

    @Headers("Content-Type: application/json")
    @GET("weather")
    fun getWeatherByCityName(
        @Query("q") cityName: String,
        @Query("appid") openWeatherToken: String,
        @Query("units") meausurementUnit: String = "metric"
    ): Observable<Weather>

    @Headers("Content-Type: application/json")
    @GET("weather")
    fun getWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") openWeatherToken: String,
        @Query("units") meausurementUnit: String = "metric"
    ): Observable<Weather>
}