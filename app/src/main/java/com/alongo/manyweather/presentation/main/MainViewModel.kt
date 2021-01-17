package com.alongo.manyweather.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alongo.manyweather.data.model.entity.weather.Weather
import com.alongo.manyweather.domain.interactor.WeatherInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val weatherInteractor: WeatherInteractor) :
    ViewModel() {
    private val _weatherData: MutableLiveData<Weather> by lazy {
        MutableLiveData<Weather>()
    }
    val weatherData: LiveData<Weather>
        get() = _weatherData

    private val _errorData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val errorData: LiveData<String>
        get() = _errorData

    private val compositeDisposable = CompositeDisposable()

    fun getWeatherByCityName(name: String) {
        errorData
        val weatherSubscription = weatherInteractor.getWeatherByCityName(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ weather: Weather ->
                _weatherData.postValue(weather)
            }, { error ->
                _errorData.postValue("${error.javaClass}: ${error.message ?: "Empty error"}")
            })
        compositeDisposable.add(weatherSubscription)
    }
}
