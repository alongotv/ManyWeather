package com.alongo.manyweather.utilities

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


fun <T : AppCompatActivity> T.replaceFragment(fragmentContainer: Int, fragment: Fragment) {
    with(supportFragmentManager) {
        beginTransaction()
            .replace(fragmentContainer, fragment)
            .commit()
    }
}
