package com.murer.rudy.naturalist

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment

fun Activity.redirectTo(cls:Class<*>) {
    val intent = Intent(this, cls)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    startActivity(intent)
    finish()
}

fun Activity.navigateTo(cls:Class<*>, extras:Map<String, String>? = null) {
    val intent = Intent(this, cls)
    if (extras != null) {
        for ((key, value) in extras)
            intent.putExtra(key, value)
    }
    startActivity(intent)
}

fun Fragment.navigateTo(cls:Class<*>, extras:Map<String, String>? = null) {
    //this.activity.navigateTo(cls, extras)
    this.activity?.navigateTo(cls, extras)
}