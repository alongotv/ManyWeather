package com.alongo.manyweather.domain.repository

interface AuthRepository {

    fun getWeatherToken(): String
}