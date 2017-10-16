package com.josiassena.movielist.movie_info.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.josiassena.core.MovieVideosResult
import com.josiassena.core.Result

/**
 * @author Josias Sena
 */
interface MovieInfoView : MvpView {
    fun updateViewsWithMovie(movie: Result)
    fun initPreviewAdapter()
    fun hidePreviews()
    fun showPreviews(result: List<MovieVideosResult>)
    fun playVideo(url: String)
}