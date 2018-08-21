package com.josiassena.movielist.full_screen_image.view

import android.os.Bundle
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.josiassena.movielist.R
import com.josiassena.movielist.full_screen_image.presenter.FullScreenPresenterImpl
import com.josiassena.movielist.movie_info.view.MovieInfoActivity.Companion.POSTER_URI
import com.josiassena.helpers.extensions.setImageFromUrlOffLine
import kotlinx.android.synthetic.main.activity_full_screen_image.*
import org.jetbrains.anko.AnkoLogger

class FullScreenImageActivity : MvpActivity<FullScreenView, FullScreenPresenterImpl>(),
        FullScreenView, AnkoLogger {

    private lateinit var url: String

    override fun createPresenter() = FullScreenPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)

        loadPoster(savedInstanceState)

        ivMoviePoster.setOnTouchListener(ImageMatrixTouchHandler(this))
    }

    private fun loadPoster(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            ivMoviePoster.setImageFromUrlOffLine(savedInstanceState.getString(POSTER_URI))
        } else {
            if (ivMoviePoster.drawable == null) {
                intent.extras.getString(POSTER_URI).let {
                    url = it
                    ivMoviePoster.setImageFromUrlOffLine(it)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(POSTER_URI, url)
    }
}
