package com.murer.rudy.naturalist

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class HomeFragment : Fragment() {

    private lateinit var activityContext: Context
    private lateinit var model: HomeFragmentModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        if (!this::activityContext.isInitialized && isAdded) {
            activityContext = activity?.applicationContext!!
        }
        if (!this::model.isInitialized) {
            model = HomeFragmentModel.newInstance(activityContext)
        }
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mlocationbox.endIconImageButton.setOnClickListener {
            doAsync {
                CURRENT_LOCATION = model.createLocationRequest()
                uiThread {
                    if (weakRef.get() != null) {
                        fillLocation(CURRENT_LOCATION)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requestLocalisationPermissions() // ask permission for location
        doAsync {
            CURRENT_LOCATION = model.createLocationRequest()
            uiThread {
                if (weakRef.get() != null) {
                    fillLocation(CURRENT_LOCATION)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        doAsync {
            val date = model.getCurrentDate()
            val time = model.getCurrentTime()
            uiThread {
                if (weakRef.get() != null) {
                    fillCurrentTime(time)
                    fillCurrentDate(date)
                }
            }
        }
    }

    private fun showProgress(show: Boolean) {
        progressbar.visibility = if (show) View.VISIBLE else View.GONE
        mscrollview.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun fillCurrentTime(time: String) {
        mtime.setText(time)
    }

    private fun fillCurrentDate(date: String) {
        mdate.setText(date)
    }

    private fun fillLocation(position: String) {
        mlocation.setText(position)
    }

    private fun requestLocalisationPermissions() {
        if (!model.isLocationPermissionGranted()) {
            val permissionsList = arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionsList, LOCATION_REQUEST_CODE)
        }
    }

    companion object {
        const val LOCATION_REQUEST_CODE = 101
        private const val UNKNOWN_LOCATION = "Unknown Location"
        private var CURRENT_LOCATION = UNKNOWN_LOCATION
    }


}


