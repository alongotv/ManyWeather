package com.alongo.manyweather.ui.fragment

import androidx.lifecycle.ViewModel
import com.alongo.manyweather.data.Weather
import com.alongo.manyweather.remote.WeatherAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class GeolocationWeatherViewModel @Inject constructor(private val weatherAPI: WeatherAPI) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val weatherSubject: BehaviorSubject<Weather> = BehaviorSubject.create()
    val errorSubject: BehaviorSubject<String?> = BehaviorSubject.create()

    fun getWeatherByGeolocation(lat: Double, lon: Double) {
        val weatherSubscription = weatherAPI.getWeatherByCoordinates(
                latitude = lat,
                longitude = lon,
                openWeatherToken = "1ce0002adc6d659ab342e693b333c059"
            ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ weather: Weather ->
                weatherSubject.onNext(weather)
            }, { error ->
                errorSubject.onNext("${error.javaClass}: ${error.message ?: "Empty error"}")
            })
        compositeDisposable.add(weatherSubscription)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
