package com.alongo.manyweather.di

import com.alongo.manyweather.ui.geolocation_weather.GeolocationWeatherFragment
import com.alongo.manyweather.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeGeolocationWeatherFragment(): GeolocationWeatherFragment
}