package com.murer.rudy.naturalist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var model: HomeFragmentModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!this::model.isInitialized) {
            model = HomeFragmentModel.newInstance()
        }

        fillDateTime()
    }


    private fun fillDateTime() {
        fillCurrentTime()
        fillCurrentDate()
    }

    private fun fillCurrentTime(){
        mtime.setText(model.getCurrentTime())
    }

    private fun fillCurrentDate(){
        mdate.setText(model.getCurrentDate())
    }




}


