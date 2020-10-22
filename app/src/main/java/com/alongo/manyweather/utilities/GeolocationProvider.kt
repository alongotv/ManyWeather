package com.alongo.manyweather.utilities

import android.content.Context
import android.location.Location
import android.os.HandlerThread
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import io.reactivex.Single


object GeolocationProvider {
    fun requestLocationUpdate(context: Context): Single<Location> {
        return Single.create {  single->
            val handlerThread = HandlerThread("GeolocationHandlerThread")
            if (!handlerThread.isAlive)
                handlerThread.start()
            val locationRequest =
                LocationRequest.create().setInterval(5000).setFastestInterval(5000)
            if (!PermissionHelper.isLocationPermissionGranted(context)) {
                single.onError(SecurityException("Location permission not granted"))
            }

            val fusedLocationApi = LocationServices.getFusedLocationProviderClient(context)

            val callback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    if (locationResult?.lastLocation != null) {
                        single.onSuccess(locationResult.lastLocation)
                    }
                    handlerThread.quitSafely()
                    fusedLocationApi.removeLocationUpdates(this)
                }
            }
            fusedLocationApi.requestLocationUpdates(locationRequest, callback, handlerThread.looper)
        }
    }
}
