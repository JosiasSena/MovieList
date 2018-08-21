package com.josiassena.movielist.favorite_movies.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.josiassena.core.Result

/**
 * @author Josias Sena
 */
interface FavoritesView : MvpView {
    fun goToLogInScreen()
    fun showEmptyListView()
    fun showFavoriteMovies(movies: ArrayList<Result>)
}