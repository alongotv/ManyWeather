package com.alongo.manyweather.data.repository

import com.alongo.manyweather.domain.repository.AuthRepository
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl: AuthRepository {
    override fun getWeatherToken(): String {
        return "1ce0002adc6d659ab342e693b333c059"
    }
}