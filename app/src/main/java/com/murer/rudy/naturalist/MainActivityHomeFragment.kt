package com.murer.rudy.naturalist

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class HomeFragment : Fragment() {

    private lateinit var model: HomeFragmentModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        if (!this::model.isInitialized) {
            model = HomeFragmentModel.newInstance()
        }
        if (!this::fusedLocationClient.isInitialized) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        }

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onStart() {
        super.onStart()
        doAsync {
            val date = model.getCurrentDate()
            val time = model.getCurrentTime()
            uiThread {
                if (weakRef.get() != null){
                    findLastLocation()
                    fillDateTime(date, time)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //if (requestingLocationUpdates) startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        //stopLocationUpdates()
    }

    private fun fillDateTime(date: String, time: String) {
        fillCurrentTime(date)
        fillCurrentDate(time)
    }

    private fun fillCurrentTime(time: String) {
        mtime.setText(time)
    }

    private fun fillCurrentDate(date: String) {
        mdate.setText(date)
    }

    private fun requestLocalisationPermissions() {
        val permissionsList = arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        )
        requestPermissions(permissionsList, LOCATION_REQUEST_CODE)
    }

    private fun findLastLocation() {
        // if permission are not granted
        if (ContextCompat.checkSelfPermission(context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context!!,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocalisationPermissions()
        }
        //asynch
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    mlocation.setText(location.toString())
                }
    }

    companion object {
        const val LOCATION_REQUEST_CODE = 101
    }


}


