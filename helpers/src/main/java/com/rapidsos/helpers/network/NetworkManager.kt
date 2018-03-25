package com.rapidsos.helpers.network

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author Josias Sena
 */
class NetworkManager(private val context: Context) {

    private val connectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private var isNetworkAvailable: Boolean = false

    fun isInternetConnectionAvailable(): Boolean {
        connectivityManager.activeNetworkInfo?.let {
            isNetworkAvailable = it.isAvailable && it.isConnected
        }

        return isNetworkAvailable
    }

}