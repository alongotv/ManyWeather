package com.alongo.manyweather.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alongo.manyweather.data.weather.Weather
import com.alongo.manyweather.remote.WeatherAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val weatherAPI: WeatherAPI) : ViewModel() {
    val weatherData: MutableLiveData<Weather> by lazy {
        MutableLiveData<Weather>()
    }

    val errorData:  MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val compositeDisposable = CompositeDisposable()

    fun getWeatherByCityName(name: String) {
        val weatherSubscription = weatherAPI.getWeatherByCityName(
                cityName = name,
                openWeatherToken = "1ce0002adc6d659ab342e693b333c059"
            ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ weather: Weather ->
                weatherData.postValue(weather)
            }, { error ->
                errorData.postValue("${error.javaClass}: ${error.message ?: "Empty error"}")
            })
        compositeDisposable.add(weatherSubscription)
    }
}
