package com.josiassena.movielist.movie_info.presenter

import android.support.customtabs.CustomTabsIntent
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.josiassena.core.MovieVideosResult
import com.josiassena.core.Result
import com.josiassena.movielist.movie_info.view.MovieInfoView

/**
 * @author Josias Sena
 */
interface MovieInfoPresenter : MvpPresenter<MovieInfoView> {
    fun getPreviewsForMovieFromId(movieId: Int)

    fun getMovieFromId(movieId: Int): Result
    fun unSubscribe()
    fun playVideoFromPreview(preview: MovieVideosResult)
    fun getCustomTabsIntent(): CustomTabsIntent
}