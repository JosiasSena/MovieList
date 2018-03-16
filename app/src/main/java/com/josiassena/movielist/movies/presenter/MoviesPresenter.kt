package com.josiassena.movielist.movies.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.josiassena.core.GenreMovieResults
import com.josiassena.movielist.movies.view.MoviesView

/**
 * @author Josias Sena
 */
interface MoviesPresenter : MvpPresenter<MoviesView> {
    fun getMoviesForGenreId(id: Int)
    fun getMoreMoviesForGenreId(id: Int, page: Int)
    fun checkDatabaseForMovies(id: Int?)
    fun saveMovieListToDatabase(results: GenreMovieResults?)
    fun checkRealmForMoviesPaginated(id: Int, page: Int)
}