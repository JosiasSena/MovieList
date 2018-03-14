package com.josiassena.movielist.movies.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.josiassena.core.GenreMovieResults
import com.josiassena.movielist.app_helpers.interfaces.LoadingView
import com.josiassena.movielist.app_helpers.interfaces.NoInternetView

/**
 * @author Josias Sena
 */
interface MoviesView : MvpView, LoadingView, NoInternetView {
    fun displayMovies(results: GenreMovieResults)
    fun refreshMovies()
    fun addMoreMovies(results: GenreMovieResults)
}