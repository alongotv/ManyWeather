package com.alongo.manyweather.presentation.geolocation_weather

import androidx.lifecycle.ViewModel
import com.alongo.manyweather.data.model.entity.weather.Weather
import com.alongo.manyweather.domain.interactor.WeatherInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class GeolocationWeatherViewModel @Inject constructor(private val weatherInteractor: WeatherInteractor) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val weatherSubject: BehaviorSubject<Weather> = BehaviorSubject.create()
    val errorSubject: BehaviorSubject<String?> = BehaviorSubject.create()

    fun getWeatherByGeolocation(lat: Double, lon: Double) {
        val weatherSubscription = weatherInteractor.getWeatherByGeolocation(
                latitude = lat,
                longitude = lon
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
