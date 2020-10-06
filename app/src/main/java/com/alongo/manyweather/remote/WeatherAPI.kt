package com.alongo.manyweather.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherAPI {

    @Headers("Content-Type: application/json")
    @GET("weather")
    fun getWeatherByCityName(@Query("q") cityName: String): Observable<String>
}