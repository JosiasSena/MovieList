package com.josiassena.movielist.movies.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.josiassena.movielist.movies.view.MoviesView

/**
 * @author Josias Sena
 */
interface MoviesPresenter : MvpPresenter<MoviesView> {
    fun getMoviesForGenreId(genreId: Int)
    fun getMoreMoviesForGenreId(genreId: Int, page: Int)
    fun getTopRatedMovies()
    fun getUpcomingMovies()
    fun getMoviesNowPlaying()
}