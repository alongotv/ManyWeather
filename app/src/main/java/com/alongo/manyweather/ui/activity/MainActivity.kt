package com.alongo.manyweather.ui.activity

import android.os.Bundle
import android.view.MenuItem
import com.alongo.manyweather.R
import com.alongo.manyweather.ui.fragment.GeolocationWeatherFragment
import com.alongo.manyweather.ui.fragment.MainFragment
import com.alongo.manyweather.utilities.replaceFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(R.id.fragmentContainer, MainFragment())
        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when(item.itemId) {
                R.id.navigation_main -> {
                replaceFragment(R.id.fragmentContainer, MainFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_geolocation-> {
                    replaceFragment(R.id.fragmentContainer, GeolocationWeatherFragment())

                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

    }
}
