package com.murer.rudy.naturalist

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.murer.rudy.naturalist.HomeFragment.Companion.LOCATION_REQUEST_CODE
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener  (loadFragment(HomeFragment()))
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener (loadFragment(ListFragment()))
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener  (loadFragment(NotificationFragment()))
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(HomeFragment())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_REQUEST_CODE -> // ask permission for camera
                if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   // do
                } //else //do

            else ->
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}
