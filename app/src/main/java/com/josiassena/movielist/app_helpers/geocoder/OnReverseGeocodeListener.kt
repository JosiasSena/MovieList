package com.josiassena.movielist.app_helpers.geocoder

import android.location.Address

/**
 * @author Josias Sena
 */
interface OnReverseGeocodeListener {

    fun onSuccess(address: Address)
    fun onError(throwable: Throwable)

}