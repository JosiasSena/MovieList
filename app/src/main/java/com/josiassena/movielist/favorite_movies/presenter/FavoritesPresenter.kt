package com.josiassena.movielist.favorite_movies.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.josiassena.movielist.favorite_movies.view.FavoritesView

/**
 * @author Josias Sena
 */
interface FavoritesPresenter : MvpPresenter<FavoritesView> {
    fun getFavoriteMovies()
    fun unsubscribe()
}