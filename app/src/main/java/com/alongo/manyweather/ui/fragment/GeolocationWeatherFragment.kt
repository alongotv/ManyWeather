package com.alongo.manyweather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.alongo.manyweather.R
import com.alongo.manyweather.ui.data.BaseFragment
import com.alongo.manyweather.utilities.GeolocationProvider
import com.alongo.manyweather.utilities.PERMISSION_GEOLOCATION_CODE
import com.alongo.manyweather.utilities.PermissionHelper
import com.alongo.manyweather.utilities.WEATHER_API_ICON_STORAGE_ENDPOINT
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.view.clicks
import com.alongo.manyweather.utilities.doBeforeFirst
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_geolocation_weather.*
import kotlinx.android.synthetic.main.fragment_geolocation_weather.view.*
import kotlinx.android.synthetic.main.layout_weather_forecast.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class GeolocationWeatherFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GeolocationWeatherViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_geolocation_weather, container, false)
        view.includedLayoutWeatherForecastGeolocationWeatherFragment.visibility = View.INVISIBLE
        return view    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = viewModelFactory.create(GeolocationWeatherViewModel::class.java)

        val weatherSubscription = viewModel.weatherSubject.doBeforeFirst {
            view?.includedLayoutWeatherForecastGeolocationWeatherFragment?.visibility = View.VISIBLE
        }.subscribe { weather ->

            val dateStr: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy | hh:mm a"))
            textViewLatestRequestDateTime.text = dateStr
            textViewLocationLayoutWeatherForecast.text = "${weather.name}, ${weather.weatherInternalParameter.country}"
            textViewCurrentTemperatureLayoutWeatherForecast.text = "${String.format("%.1f", weather.weatherData.temperature)}"
            weather.weatherStateList.first().let {
                textViewWeatherStateWeatherForecastLayout.text = it.title
                Glide.with(inflaterContext).load(WEATHER_API_ICON_STORAGE_ENDPOINT + it.iconName + ".png").into(imageViewWeatherStateWeatherForecastLayout)
            }
            textViewMaxTempLayoutWeatherForecast.text =  "${String.format("%.1f", weather.weatherData.temp_max)} °C"
            textViewMinTempLayoutWeatherForecast.text =  "${String.format("%.1f", weather.weatherData.temp_min)} °C"
            textViewHumidityLayoutWeatherForecast.text = "${weather.weatherData.humidity} %"
            textViewPressureLayoutWeatherForecast.text = "${weather.weatherData.pressure} hPa"
            textViewWindLayoutWeatherForecast.text = "${String.format("%.1f", weather.windState.speed)} m/s"
        }

        compositeDisposable.add(weatherSubscription)

        val errorSubscription = viewModel.errorSubject.subscribe { errorMessage ->
            Toast.makeText(inflaterContext, errorMessage, Toast.LENGTH_LONG).show()
        }

        buttonUpdateWeatherFragmentWeatherGeolocation.clicks().subscribe {
            getCurrentWeather()
        }

        compositeDisposable.add(errorSubscription)
    }

    private fun getCurrentWeather() {
        if (PermissionHelper.isLocationPermissionGranted(inflaterContext)) {
            val geoLocationObservable =
                GeolocationProvider.requestLocationUpdate(inflaterContext)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ location ->
                    viewModel.getWeatherByGeolocation(location.latitude, location.longitude)
                }, { error ->
                    Toast.makeText(inflaterContext, error.localizedMessage, Toast.LENGTH_LONG).show()
                })
            compositeDisposable.add(geoLocationObservable)
        } else {
            PermissionHelper.requestLocationPermission(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_GEOLOCATION_CODE -> {
                getCurrentWeather()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
