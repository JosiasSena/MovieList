package com.josiassena.movielist.genres.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.josiassena.core.Genre
import com.josiassena.movielist.genres.view.GenreView
import com.mancj.materialsearchbar.MaterialSearchBar

/**
 * @author Josias Sena
 */
interface GenrePresenter : MvpPresenter<GenreView> {
    fun getGenres()
    fun isNetworkAvailable(): Boolean
    fun unSubscribe()
    fun queryGenres(query: String): List<Genre>?
    fun listenToSearchViewChanges(genreSearchView: MaterialSearchBar)
    fun checkIsNetworkAvailable()
}