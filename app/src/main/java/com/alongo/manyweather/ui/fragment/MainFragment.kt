package com.alongo.manyweather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.alongo.manyweather.R
import com.alongo.manyweather.ui.data.BaseFragment
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = viewModelFactory.create(MainViewModel::class.java)

        val weatherSubscription = viewModel.weatherSubject
            .subscribe { weather ->
            textViewLocation.text =
                "${weather.name}, ${weather.weatherInternalParameter.country}"
            textViewTemperature.text =
                "${weather.weatherData.temperature} °C"
        }
        val errorSubscription = viewModel.errorSubject.subscribe { errorMessage ->
            textInputLayout.error = errorMessage
        }
        compositeDisposable.add(weatherSubscription)
        compositeDisposable.add(errorSubscription)

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
}
