package com.josiassena.movielist.top_rated_movies.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.josiassena.movielist.top_rated_movies.view.TopRatedMoviesView

/**
 * @author Josias Sena
 */
interface TopRatedMoviesPresenter : MvpPresenter<TopRatedMoviesView> {

    fun getTopRatedMovies()

}