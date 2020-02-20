package com.yaman.core.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast


object ConnectionUtil {

     fun isNetworkAvailable(context: Context): Boolean {
        var networkAvailable = false
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo

            if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting) {
                networkAvailable = true
            } else {
                networkAvailable = false
                Toast.makeText(context as Activity, com.yaman.core.R.string.connection_failed, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return networkAvailable
    }
}