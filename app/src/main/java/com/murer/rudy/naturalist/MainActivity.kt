package com.murer.rudy.naturalist

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import android.R.attr.fragment
import android.util.Log


class MainActivity : AppCompatActivity(), HomeFragment.TimePickerFragment.OnDateReceiveCallBack {


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

    override fun onDateReceive(hourOfDay: Int, minute: Int) {
        Log.d("onDateReceive",hourOfDay.toString() + ":" + minute.toString())
    }
}
