package com.josiassena.movielist.movies.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.josiassena.core.MovieResults
import com.josiassena.movielist.app_helpers.interfaces.LoadingView
import com.josiassena.movielist.app_helpers.interfaces.NoInternetView

/**
 * @author Josias Sena
 */
interface MoviesView : MvpView, LoadingView, NoInternetView {
    fun displayMovies(results: MovieResults)
    fun refreshMovies()
    fun addMoreMovies(results: MovieResults)
    fun hideMovies()
    fun showMovies()
}