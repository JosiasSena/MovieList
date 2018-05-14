package com.josiassena.movielist.home.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.josiassena.movielist.home.view.HomeView

/**
 * @author Josias Sena
 */
interface HomePresenter : MvpPresenter<HomeView> {
    fun getTop5RatedMovies()
    fun get5NowPlayingMovies()
    fun get5UpcomingMovies()
}