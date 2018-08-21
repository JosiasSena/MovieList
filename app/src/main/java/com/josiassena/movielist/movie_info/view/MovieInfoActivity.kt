package com.josiassena.movielist.movie_info.view

import android.app.DownloadManager
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AlphaAnimation
import android.view.animation.LinearInterpolator
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.josiassena.core.MovieVideosResult
import com.josiassena.core.Result
import com.josiassena.movielist.R
import com.josiassena.movielist.app_helpers.constants.MOVIE_ID_KEY
import com.josiassena.movielist.app_helpers.constants.POSTER_BASE_URL
import com.josiassena.movielist.full_screen_image.view.FullScreenImageActivity
import com.josiassena.movielist.movie_info.presenter.MovieInfoPresenterImpl
import com.josiassena.movielist.movie_info.receiver.PosterDownloadBroadcastReceiver
import com.josiassena.movielist.movie_info.view.rec_view.MovieInfoAdapter
import com.rapidsos.helpers.extensions.hide
import com.rapidsos.helpers.extensions.setImageFromUrlOffLine
import com.rapidsos.helpers.extensions.show
import com.rapidsos.helpers.extensions.showLongSnackBar
import kotlinx.android.synthetic.main.content_movie_info.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.AnkoLogger

class MovieInfoActivity : MvpActivity<MovieInfoView, MovieInfoPresenterImpl>(), MovieInfoView,
        AnkoLogger {

    private val animation = AlphaAnimation(0f, 1f).apply {
        duration = 500
        interpolator = LinearInterpolator()
    }
    private val downloadReceiver = PosterDownloadBroadcastReceiver()

    private lateinit var adapter: MovieInfoAdapter
    private lateinit var result: Result
    private var movieId: Int = 0
    private var isFavorite: Boolean = false

    companion object {
        const val POSTER_URI = "poster_uri"
    }

    override fun createPresenter(): MovieInfoPresenterImpl = MovieInfoPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_info)
        setSupportActionBar(toolbar)

        adapter = MovieInfoAdapter(presenter)

        savedInstanceState?.let {
            movieId = savedInstanceState.getInt(MOVIE_ID_KEY)

            presenter.getMovieFromId(movieId).subscribe { updateViewsWithMovie(it) }
        }

        movieId = savedInstanceState?.getInt(MOVIE_ID_KEY) ?: intent.extras.getInt(MOVIE_ID_KEY)

        presenter.getMovieFromId(movieId).subscribe {
            this.result = it
            updateViewsWithMovie(result)
        }

        ivMoviePoster.setOnClickListener { goToFullScreenActivity(result) }

        tvDownloadPoster.setOnClickListener { downloadMoviePosterForCurrentMovie() }

        registerReceiver(downloadReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        registerReceiver(downloadReceiver, IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED))

        llAddToFavorite.setOnClickListener {
            if (isFavorite) {
                presenter.removeMovieFromFavorites(movieId)
            } else {
                presenter.addMovieToFavorites(movieId)
            }
        }

        presenter.checkIfIsFavoriteMovie(movieId)
    }

    private fun downloadMoviePosterForCurrentMovie() {
        val uri = Uri.parse(POSTER_BASE_URL + result.posterPath)
        val request = DownloadManager.Request(uri).apply {
            setTitle(getString(R.string.title_downloading_movie_poster))
            setDescription("Downloading movie poster for ${result.title}")
            setDestinationInExternalFilesDir(this@MovieInfoActivity,
                    Environment.DIRECTORY_DOWNLOADS, "${result.title}.jpg")
        }

        presenter.downloadMoviePoster(request)
    }

    override fun showAddToFavorites() = llAddToFavorite.show()

    override fun hideAddToFavorites() = llAddToFavorite.hide()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_share -> {
                val sharingIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TITLE, result.title)
                    putExtra(Intent.EXTRA_SUBJECT, result.title)

                    val body = "Release date: ${result.releaseDate}\n\nOverview: ${result.overview}"
                    putExtra(Intent.EXTRA_TEXT, body)
                }

                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)))
            }
        }

        return super.onOptionsItemSelected(item)
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

            supportActionBar?.let { it.title = movie.title }

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

    private fun getMovieVideos(movie: Result) = presenter.getPreviewsForMovieFromId(movie.id as Int)

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

    override fun playVideo(url: String) {
        presenter.getCustomTabsIntent().launchUrl(this, Uri.parse(url))
    }

    override fun showNoInternetConnectionError() {
        showLongSnackBar(tvDownloadPoster, R.string.no_internet_click_again)
    }

    override fun showEmptyStateView() {
    }

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
        unregisterReceiver(downloadReceiver)

        animation.cancel()

        presenter.unSubscribe()
    }

    override fun showMovieIsFavoriteView() {
        isFavorite = true
        ivFavorite.setImageResource(R.drawable.ic_star_filled)
    }

    override fun showMovieIsNotFavoriteView() {
        isFavorite = false
        ivFavorite.setImageResource(R.drawable.ic_star_unfilled)
    }
}
