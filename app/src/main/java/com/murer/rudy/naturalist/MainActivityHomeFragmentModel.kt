package com.murer.rudy.naturalist

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.support.v4.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import org.jetbrains.anko.toast
import java.util.*

interface HomeFragmentModel {

    companion object {
        fun newInstance(activityContext: Context):
                HomeFragmentModelImpl = HomeFragmentModelImpl(
                        // add param here
                activityContext,
                FusedLocationProviderClient(activityContext),
                SettingsClient(activityContext),
                activityContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager,
                PermissionManagerImpl(activityContext)
                )

    }

    // add functions here
    fun getCurrentTime(): String

    fun getCurrentDate(): String

    fun createLocationRequest(): String

    fun isLocationPermissionGranted(): Boolean
}
// add data class here

class HomeFragmentModelImpl(
        // add Managers here
        private val activityContext: Context,
        private val fusedLocationClient: FusedLocationProviderClient,
        private val settingsClient: SettingsClient,
        private val locationManager: LocationManager,
        private val permissionManager: PermissionManager
) : HomeFragmentModel {

    override fun createLocationRequest(): String {
        val locationRequest = LocationRequest().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val task = LocationServices.getSettingsClient(activityContext).checkLocationSettings(builder.build())
        task.addOnCompleteListener { locationSettingsResponse ->
            if (ContextCompat.checkSelfPermission(activityContext,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(activityContext,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                isGPSEnabled()

                if (locationSettingsResponse.isSuccessful) {
                    val locationSettingsRequest = builder.build()
                    settingsClient.checkLocationSettings(locationSettingsRequest)
                    fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult?) {
                            CURRENT_LOCATION = onLocationChanged(locationResult!!.lastLocation)
                        }
                    }, Looper.myLooper())
                }
            }
        }

        task.addOnFailureListener { _ ->
            try {
                lastKnownLocation()
            } catch (exception: Exception) {
                activityContext.toast(R.string.error_occured)
            }
        }
        return CURRENT_LOCATION
    }

    private fun isGPSEnabled() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            activityContext.toast(R.string.enable_GPS)
        }
    }

    private fun onLocationChanged(location: Location?): String {
        when (location) {
            null -> {
                return UNKNOWN_LOCATION
            }
            else -> {
                return LatLng(location.latitude, location.longitude).toString()
            }
        }
    }

    private fun lastKnownLocation() {
        if (ContextCompat.checkSelfPermission(activityContext,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activityContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        CURRENT_LOCATION = LatLng(location!!.latitude, location.longitude).toString()
                    }
                    .addOnFailureListener {
                        CURRENT_LOCATION = UNKNOWN_LOCATION
                    }
        }

    }

    override fun isLocationPermissionGranted(): Boolean {
        return permissionManager.isLocationPermissionGranted()
    }


    override fun getCurrentTime(): String {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        return StringBuilder().append(addZeroIfNumberIsASingleDigit(hour))
                .append(":").append(addZeroIfNumberIsASingleDigit(minute)).toString()
    }

    override fun getCurrentDate(): String {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH) + 1 // month is 0 to 11
        val year = c.get(Calendar.YEAR)
        return StringBuilder().append(addZeroIfNumberIsASingleDigit(day)).append("/")
                .append(addZeroIfNumberIsASingleDigit(month)).append("/").append(year).toString()
    }

    private fun addZeroIfNumberIsASingleDigit(number: Int): String {
        if (number < 10) {
            return "0$number"
        }
        return number.toString()
    }

    companion object {
        private const val UNKNOWN_LOCATION = "Unknown Location"
        private var CURRENT_LOCATION = UNKNOWN_LOCATION
        const val LOCATION_REQUEST_CODE = 101
    }




}