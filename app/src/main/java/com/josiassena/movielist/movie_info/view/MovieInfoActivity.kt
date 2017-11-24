package com.josiassena.movielist.movie_info.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AlphaAnimation
import android.view.animation.LinearInterpolator
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.josiassena.core.MovieVideosResult
import com.josiassena.core.Result
import com.josiassena.movielist.R
import com.josiassena.movielist.full_screen_image.view.FullScreenImageActivity
import com.josiassena.movielist.movie_info.presenter.MovieInfoPresenterImpl
import com.josiassena.movielist.movie_info.view.rec_view.MovieInfoAdapter
import com.josiassena.movielist.movies.view.rec_view.MOVIE_ID_KEY
import com.josiassena.movielist.movies.view.rec_view.POSTER_BASE_URL
import com.rapidsos.helpers.extensions.hide
import com.rapidsos.helpers.extensions.setImageFromUrlOffLine
import com.rapidsos.helpers.extensions.show
import kotlinx.android.synthetic.main.content_movie_info.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.AnkoLogger

val POSTER_URI = "poseter_uri"

class MovieInfoActivity : MvpActivity<MovieInfoView, MovieInfoPresenterImpl>(), MovieInfoView,
        AnkoLogger {

    private val animation = AlphaAnimation(0f, 1f).apply {
        duration = 500
        interpolator = LinearInterpolator()
    }

    private lateinit var adapter: MovieInfoAdapter
    private lateinit var result: Result
    private var movieId: Int = 0

    override fun createPresenter(): MovieInfoPresenterImpl = MovieInfoPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_info)
        setSupportActionBar(toolbar)

        adapter = MovieInfoAdapter(presenter)

        savedInstanceState?.let {
            movieId = savedInstanceState.getInt(MOVIE_ID_KEY)

            presenter.getMovieFromId(movieId).subscribe { result ->
                updateViewsWithMovie(result)
            }
        }

        movieId = if (savedInstanceState != null) {
            savedInstanceState.getInt(MOVIE_ID_KEY)
        } else {
            val extras = intent.extras
            extras.getInt(MOVIE_ID_KEY)
        }

        presenter.getMovieFromId(movieId).subscribe { result ->
            this.result = result
            updateViewsWithMovie(result)
        }

        ivMoviePoster.setOnClickListener { goToFullScreenActivity(result) }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(MOVIE_ID_KEY, movieId)
    }

    private fun goToFullScreenActivity(movie: Result) {
        val transitionName = ivMoviePoster.transitionName
        val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, ivMoviePoster, transitionName)

        val intent = Intent(this, FullScreenImageActivity::class.java).apply {
            putExtras(Bundle().apply {
                putString(POSTER_URI, POSTER_BASE_URL + movie.posterPath)
            })
        }

        startActivity(intent, options.toBundle())
    }

    override fun updateViewsWithMovie(movie: Result) {
        runOnUiThread {
            loadMoviePoster(movie)

            supportActionBar?.let {
                it.title = movie.title
            }

            tvReleaseDate.text = resources.getString(R.string.release_date, movie.releaseDate)
            tvDescription.text = movie.overview
        }
    }

    private fun loadMoviePoster(movie: Result) {
        val url = POSTER_BASE_URL + movie.posterPath
        ivMoviePoster.setImageFromUrlOffLine(url)

        startAnimations()

        getMovieVideos(movie)
    }

    private fun startAnimations() {
        tvDescription.startAnimation(animation)
        tvReleaseDate.startAnimation(animation)
    }

    private fun getMovieVideos(movie: Result) = presenter.getPreviewsForMovieFromId(movie.id)

    override fun initPreviewAdapter() {
        rvPreviews.adapter = adapter
        rvPreviews.setItemViewCacheSize(100)
        rvPreviews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun hidePreviews() = rvPreviews.hide()

    override fun showPreviews(result: List<MovieVideosResult>) {
        rvPreviews.show()

        adapter.setPreviews(result)

        initPreviewAdapter()
    }

    override fun playVideo(url: String) = presenter.getCustomTabsIntent().launchUrl(this, Uri.parse(url))

    override fun onPause() {
        super.onPause()
        animation.cancel()
    }

    override fun onStop() {
        super.onStop()
        animation.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        animation.cancel()
        presenter.unSubscribe()
    }
}
