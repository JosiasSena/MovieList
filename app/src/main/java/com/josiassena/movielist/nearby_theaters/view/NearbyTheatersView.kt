package com.josiassena.movielist.nearby_theaters.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.josiassena.googleplacesapi.models.Place

/**
 * @author Josias Sena
 */
interface NearbyTheatersView : MvpView {
    fun displayMovieTheaters(places: List<Place>?)
}