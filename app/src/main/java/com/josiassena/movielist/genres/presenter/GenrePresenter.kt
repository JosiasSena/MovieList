package com.josiassena.movielist.genres.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.josiassena.movielist.genres.view.GenreView

/**
 * @author Josias Sena
 */
interface GenrePresenter : MvpPresenter<GenreView> {
    fun getGenres()
    fun isNetworkAvailable(): Boolean
    fun unSubscribe()
}