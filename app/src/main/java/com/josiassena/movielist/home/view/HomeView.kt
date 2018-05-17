package com.josiassena.movielist.home.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.josiassena.core.Result

/**
 * @author Josias Sena
 */
interface HomeView : MvpView {
    fun onGotTopRatedMovies(top5: List<Result>)
    fun onGotNowPlayingMovies(top5: List<Result>)
    fun onGotUpcomingMovies(top5: List<Result>)
    fun displayTop5EmptyStateView()
    fun displayUpcomingEmptyStateView()
    fun displayNowPlayingEmptyStateView()
    fun dismissTop5EmptyStateView()
    fun dismissUpcomingEmptyStateView()
    fun dismissNowPlayingEmptyStateView()
}