package com.josiassena.movielist.nearby_theaters.presenter

import android.location.Location
import android.os.Looper
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.josiassena.movielist.nearby_theaters.view.NearbyTheatersView

/**
 * @author Josias Sena
 */
interface NearbyTheatersPresenter : MvpPresenter<NearbyTheatersView> {

    fun getMovieTheatersNearby(apiKey: String, location: Location)
    fun getMapsLooper(): Looper
    fun terminate()
    fun unSubscribe()
    fun getAddressFromLocation(location: Location)
}