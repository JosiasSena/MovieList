package com.josiassena.helpers

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

/**
 * @author Josias Sena
 */
object PermissionUtils {

    fun requestLocationPermission(activity: Activity, requestCode: Int) {
        ActivityCompat.requestPermissions(activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestCode
        )
    }

    fun isLocationPermissionGranted(context: Context): Boolean {
        val checkSelfPermission = ActivityCompat
                .checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)

        return checkSelfPermission == PackageManager.PERMISSION_GRANTED
    }

    fun isPermissionGranted(context: Context, permission: String): Boolean {
        val checkSelfPermission = ActivityCompat.checkSelfPermission(context, permission)

        return checkSelfPermission == PackageManager.PERMISSION_GRANTED
    }

}