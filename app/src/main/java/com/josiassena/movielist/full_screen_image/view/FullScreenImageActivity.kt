package com.josiassena.movielist.full_screen_image.view

import android.graphics.Matrix
import android.os.Bundle
import android.view.ScaleGestureDetector
import android.widget.ImageView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.josiassena.movielist.R
import com.josiassena.movielist.full_screen_image.presenter.FullScreenPresenterImpl
import com.josiassena.movielist.movie_info.view.POSTER_URI
import com.rapidsos.helpers.extensions.setImageFromUrlOffLine
import kotlinx.android.synthetic.main.activity_full_screen_image.*
import org.jetbrains.anko.AnkoLogger

class FullScreenImageActivity : MvpActivity<FullScreenView, FullScreenPresenterImpl>(),
        FullScreenView, AnkoLogger {

    private val savedMatrix = Matrix()
    private val savedMatrix2 = Matrix()
    private val matrix = Matrix()

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var url: String

    private var scaleFactor: Float = 0f

    companion object {
        private val NONE = 0
        private val DRAG = 1
        private val ZOOM = 2

        private var mode = NONE
    }

    override fun createPresenter() = FullScreenPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)

        loadPoster(savedInstanceState)

        implementZoomFeature()

        implementDragFeature()
    }

    private fun implementZoomFeature() {
        scaleGestureDetector = ScaleGestureDetector(this,
                object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

                    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
                        mode = ZOOM
                        return true
                    }

                    override fun onScale(detector: ScaleGestureDetector): Boolean {
                        ivMoviePoster.scaleType = ImageView.ScaleType.MATRIX

                        scaleFactor *= detector.scaleFactor
                        scaleFactor = Math.max(1.8f, Math.min(scaleFactor, 10.0f))

                        // TODO("Fix pinch to zoom")
                        val pivotX = detector.currentSpan.div(2)
                        val pivotY = detector.currentSpan.div(2)
                        matrix.setScale(scaleFactor, scaleFactor, pivotX, pivotY)

                        ivMoviePoster.imageMatrix = matrix

                        if (scaleFactor == 1.8f) {
                            ivMoviePoster.scaleType = ImageView.ScaleType.FIT_CENTER
                        }

                        return true
                    }

                    override fun onScaleEnd(detector: ScaleGestureDetector?) {
                        super.onScaleEnd(detector)
                        mode = NONE
                    }
                })
    }

    private fun implementDragFeature() {
        ivMoviePoster.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            true
        }
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
