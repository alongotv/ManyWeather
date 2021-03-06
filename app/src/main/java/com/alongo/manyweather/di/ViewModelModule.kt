package com.alongo.manyweather.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alongo.manyweather.presentation.geolocation_weather.GeolocationWeatherViewModel
import com.alongo.manyweather.presentation.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GeolocationWeatherViewModel::class)
    abstract fun bindGeolocationWeatherViewModel(geolocationWeatherViewModel: GeolocationWeatherViewModel): ViewModel
}