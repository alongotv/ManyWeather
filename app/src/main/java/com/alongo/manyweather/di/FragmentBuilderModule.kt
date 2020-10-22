package com.alongo.manyweather.di

import com.alongo.manyweather.ui.fragment.GeolocationWeatherFragment
import com.alongo.manyweather.ui.fragment.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeGeolocationWeatherFragment(): GeolocationWeatherFragment
}