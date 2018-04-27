package com.josiassena.movielist.home.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.josiassena.movielist.home.view.HomeView

/**
 * @author Josias Sena
 */
interface HomePresenter : MvpPresenter<HomeView> {
    fun getTopRatedMovies()
    fun getNowPlayingMovies()
    fun getUpcomingMovies()
}