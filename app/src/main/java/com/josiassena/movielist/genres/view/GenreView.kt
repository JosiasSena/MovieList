package com.josiassena.movielist.genres.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.josiassena.core.Genres
import com.josiassena.movielist.app_helpers.view_interfaces.LoadingView
import com.josiassena.movielist.app_helpers.view_interfaces.NoInternetView

/**
 * @author Josias Sena
 */
interface GenreView : MvpView, LoadingView, NoInternetView {
    fun displayGenres(genres: Genres)
    fun showRecyclerView()
}