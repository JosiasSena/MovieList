package com.josiassena.movielist.movies.view

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.josiassena.core.Genre
import com.josiassena.core.MovieResults
import com.josiassena.movielist.R
import com.josiassena.movielist.app_helpers.constants.QUERY_KEY
import com.josiassena.movielist.app_helpers.constants.QueryTypes
import com.josiassena.movielist.genres.view.rec_view.KEY_GENRE
import com.josiassena.movielist.movies.presenter.MoviesDisposableLifeCycleObserver
import com.josiassena.movielist.movies.presenter.MoviesPresenterImpl
import com.josiassena.movielist.movies.view.rec_view.MoviesAdapter
import com.josiassena.helpers.extensions.hide
import com.josiassena.helpers.extensions.show
import com.josiassena.helpers.extensions.showLongSnackBar
import kotlinx.android.synthetic.main.content_movies.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.AnkoLogger

class MoviesActivity : MvpActivity<MoviesView, MoviesPresenterImpl>(), MoviesView, AnkoLogger {

    private val movieAdapter = MoviesAdapter()
    private val currentQueryType by lazy { intent?.extras?.getString(QUERY_KEY) }

    private var currentResults: MovieResults? = null
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var currentGenre: Genre? = null

    companion object {
        private const val MOVIES_KEY = "movies_key"
    }

    override fun createPresenter() = MoviesPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        setSupportActionBar(toolbar)
        toolbar.transitionName = "genre_title"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lifecycle.addObserver(MoviesDisposableLifeCycleObserver)

        initRecView()

        updateActionBarUi()

        movieRefreshLayout.setOnRefreshListener { refreshMovies() }

        refreshMovies()
    }

    private fun updateActionBarUi() {
        when (currentQueryType) {
            QueryTypes.GENRE.name -> {
                currentGenre = intent?.extras?.getParcelable(KEY_GENRE)
                supportActionBar?.title = currentGenre?.name
            }
            QueryTypes.TOP_MOVIES.name -> {
                supportActionBar?.title = getString(R.string.top_movies)
            }
            QueryTypes.UPCOMING_MOVIES.name -> {
                supportActionBar?.title = getString(R.string.upcoming_movies)
            }
            QueryTypes.NOW_PLAYING.name -> {
                supportActionBar?.title = getString(R.string.now_playing)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putParcelable(MOVIES_KEY, currentResults)

            rvMovies.layoutManager.onSaveInstanceState()
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        currentResults = savedInstanceState?.getParcelable(MOVIES_KEY)

        rvMovies.layoutManager.onRestoreInstanceState(savedInstanceState)

        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun initRecView() {
        rvMovies.let {
            it.apply {
                layoutManager = getProperGridLayoutManager()
                adapter = movieAdapter
                setItemViewCacheSize(100)
            }

            currentResults?.let { movieAdapter.addMovies(it.results) }

            enablePagination(it.layoutManager as GridLayoutManager)
        }
    }

    private fun getProperGridLayoutManager(): GridLayoutManager {
        val screenLayout = resources.configuration.screenLayout
        val currentScreenSize = screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK

        return when (currentScreenSize) {
            Configuration.SCREENLAYOUT_SIZE_LARGE,
            Configuration.SCREENLAYOUT_SIZE_XLARGE -> GridLayoutManager(this, 3)
            else -> GridLayoutManager(this, 2)
        }
    }

    private fun enablePagination(layoutManager: GridLayoutManager) {
        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    val isAtEnd = (visibleItemCount + firstVisibleItemPosition) >= totalItemCount

                    if (isAtEnd && (firstVisibleItemPosition >= 0) && (totalItemCount >= 20)) {
                        val nextPage = currentResults?.page?.plus(1)
                        if (nextPage == currentResults?.totalPages) {
                            isLastPage = true
                        }

                        getMoreMoviesForGenreId(nextPage)
                    }
                }
            }
        })
    }

    private fun getMoreMoviesForGenreId(nextPage: Int?) {
        currentGenre?.id?.let { presenter.getMoreMoviesForGenreId(it, nextPage as Int) }
    }

    override fun onPause() {
        super.onPause()
        hideLoading()
    }

    override fun showLoading() {
        isLoading = true

        runOnUiThread { movieRefreshLayout.isRefreshing = true }
    }

    override fun hideLoading() {
        isLoading = false

        runOnUiThread { movieRefreshLayout.isRefreshing = false }
    }

    override fun displayMovies(results: MovieResults) {
        showMovies()

        currentResults = results

        runOnUiThread { movieAdapter.addMovies(results.results) }
    }

    override fun showEmptyStateView() = hideMovies()

    override fun hideMovies() {
        runOnUiThread {
            tvNoMovies.show()
            rvMovies.hide()
        }
    }

    override fun showNoInternetConnectionError() = showLongSnackBar(toolbar, R.string.no_internet)

    override fun refreshMovies() {
        when (currentQueryType) {
            QueryTypes.GENRE.name -> {
                currentGenre?.id?.let { presenter.getMoviesForGenreId(it) }
            }
            QueryTypes.TOP_MOVIES.name -> {
                presenter.getTopRatedMovies()
            }
            QueryTypes.UPCOMING_MOVIES.name -> {
                presenter.getUpcomingMovies()
            }
            QueryTypes.NOW_PLAYING.name -> {
                presenter.getMoviesNowPlaying()
            }
        }
    }

    override fun addMoreMovies(results: MovieResults) {
        showMovies()

        currentResults = results
        movieAdapter.addMoreMovies(results.results)
    }

    override fun showMovies() {
        runOnUiThread {
            tvNoMovies.hide()
            rvMovies.show()
        }
    }
}
