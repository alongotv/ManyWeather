package com.alongo.manyweather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.alongo.manyweather.R
import com.alongo.manyweather.data.weather.Weather
import com.alongo.manyweather.ui.data.BaseFragment
import com.alongo.manyweather.utilities.WEATHER_API_ICON_STORAGE_ENDPOINT
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.widget.textChanges
import com.alongo.manyweather.utilities.doBeforeFirst
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.layout_weather_forecast.*
import kotlinx.coroutines.flow.onEach
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        view.includedLayoutWeatherForecastMainFragment.visibility = View.INVISIBLE
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = viewModelFactory.create(MainViewModel::class.java)

        viewModel.weatherData
            .asFlow()
            .doBeforeFirst { view?.includedLayoutWeatherForecastMainFragment?.visibility = View.VISIBLE }
            .onEach { textInputLayout.error = null }
            .asLiveData()
            .observe(viewLifecycleOwner, Observer<Weather> { weather ->
                bindWeatherToUi(weather)
            })
        viewModel.errorData.observe(viewLifecycleOwner, Observer<String> { errorMessage ->
            textInputLayout.error = errorMessage
        })

        editTextCity.textChanges()
            .doOnEach { textInputLayout.error = null }
            .filter { it.length >= 2 }
            .map { it.toString() }.throttleLast(
                2L, TimeUnit.SECONDS
            )
            .subscribe { cityNameString ->
                viewModel.getWeatherByCityName(cityNameString)
            }
    }

    private fun bindWeatherToUi(weather: Weather) {
        val dateStr: String =
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy | hh:mm a"))
        textViewLatestRequestDateTime.text = dateStr
        textViewLocationLayoutWeatherForecast.text =
            "${weather.name}, ${weather.weatherInternalParameter.country}"
        textViewCurrentTemperatureLayoutWeatherForecast.text =
            "${String.format("%.1f", weather.weatherData.temperature)}"
        weather.weatherStateList.first().let {
            textViewWeatherStateWeatherForecastLayout.text = it.title
            Glide.with(inflaterContext)
                .load(WEATHER_API_ICON_STORAGE_ENDPOINT + it.iconName + ".png")
                .into(imageViewWeatherStateWeatherForecastLayout)
        }
        textViewMaxTempLayoutWeatherForecast.text =
            "${String.format("%.1f", weather.weatherData.temp_max)} °C"
        textViewMinTempLayoutWeatherForecast.text =
            "${String.format("%.1f", weather.weatherData.temp_min)} °C"
        textViewHumidityLayoutWeatherForecast.text = "${weather.weatherData.humidity} %"
        textViewPressureLayoutWeatherForecast.text = "${weather.weatherData.pressure} hPa"
        textViewWindLayoutWeatherForecast.text =
            "${String.format("%.1f", weather.windState.speed)} m/s"
    }
}
