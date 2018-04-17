package com.josiassena.movielist.movies.view

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.josiassena.core.Genre
import com.josiassena.core.GenreMovieResults
import com.josiassena.movielist.R
import com.josiassena.movielist.genres.view.rec_view.KEY_GENRE
import com.josiassena.movielist.movies.presenter.MoviesDisposableLifeCycleObserver
import com.josiassena.movielist.movies.presenter.MoviesPresenterImpl
import com.josiassena.movielist.movies.view.rec_view.MoviesAdapter
import kotlinx.android.synthetic.main.content_movies.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.AnkoLogger

class MoviesActivity : MvpActivity<MoviesView, MoviesPresenterImpl>(), MoviesView, AnkoLogger {

    private lateinit var movieAdapter: MoviesAdapter

    private var currentResults: GenreMovieResults? = null
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

        lifecycle.addObserver(MoviesDisposableLifeCycleObserver)

        initRecView()

        savedInstanceState?.let {
            currentResults = it.getParcelable(MOVIES_KEY)
            rvMovies.layoutManager.onRestoreInstanceState(savedInstanceState)
        }

        getCurrentGenre()

        movieRefreshLayout.setOnRefreshListener { refreshMovies() }

        refreshMovies()
    }

    private fun getCurrentGenre() {
        currentGenre = intent?.extras?.getParcelable(KEY_GENRE)
        supportActionBar?.title = currentGenre?.name
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putParcelable(MOVIES_KEY, currentResults)

            rvMovies.layoutManager.onSaveInstanceState()
        }
        super.onSaveInstanceState(outState)
    }

    private fun initRecView() {
        val layoutManager = getProperGridLayoutManager()
        movieAdapter = MoviesAdapter()

        rvMovies.layoutManager = layoutManager
        rvMovies.adapter = movieAdapter
        rvMovies.setItemViewCacheSize(100)

        currentResults?.let { movieAdapter.addMovies(it.results) }

        enablePagination(layoutManager)
    }

    private fun getProperGridLayoutManager(): GridLayoutManager {
        val currentDeviceScreenSize = resources.configuration.screenLayout and
                Configuration.SCREENLAYOUT_SIZE_MASK

        return when (currentDeviceScreenSize) {
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
        runOnUiThread {
            isLoading = true
            movieRefreshLayout.isRefreshing = true
        }
    }

    override fun hideLoading() {
        runOnUiThread {
            isLoading = false
            movieRefreshLayout.isRefreshing = false
        }
    }

    override fun displayMovies(results: GenreMovieResults) {
        runOnUiThread {
            tvNoMovies.visibility = View.GONE
            rvMovies.visibility = View.VISIBLE

            currentResults = results

            movieAdapter.addMovies(results.results)
        }
    }

    override fun showEmptyStateView() {
        runOnUiThread {
            rvMovies.visibility = View.GONE
            tvNoMovies.visibility = View.VISIBLE
        }
    }

    override fun showNoInternetConnectionError() {
        Snackbar.make(toolbar, R.string.no_internet, Snackbar.LENGTH_LONG).show()
    }

    override fun refreshMovies() {
        currentGenre?.id?.let { presenter.getMoviesForGenreId(it) }
    }

    override fun addMoreMovies(results: GenreMovieResults) {
        tvNoMovies.visibility = View.GONE
        rvMovies.visibility = View.VISIBLE

        currentResults = results
        movieAdapter.addMoreMovies(results.results)
    }
}
