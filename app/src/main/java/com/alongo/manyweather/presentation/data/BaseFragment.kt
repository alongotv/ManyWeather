package com.alongo.manyweather.presentation.data

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

open class BaseFragment: DaggerFragment() {
    lateinit var inflaterContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflaterContext = inflater.context
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}