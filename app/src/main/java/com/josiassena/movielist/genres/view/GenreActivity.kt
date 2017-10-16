package com.josiassena.movielist.genres.view

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.josiassena.core.Genres
import com.josiassena.movielist.R
import com.josiassena.movielist.app.App
import com.josiassena.movielist.genres.presenter.GenrePresenterImpl
import com.josiassena.movielist.genres.view.rec_view.GenresAdapter
import com.rapidsos.helpers.extensions.hide
import com.rapidsos.helpers.extensions.show
import com.rapidsos.helpers.extensions.showLongSnackBar
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.android.synthetic.main.content_genre.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.AnkoLogger

class GenreActivity : MvpActivity<GenreView, GenrePresenterImpl>(), GenreView, AnkoLogger {

    private val genresAdapter = GenresAdapter()

    override fun createPresenter() = GenrePresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre)
        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.movie_genres)

        initRecView()

        refreshLayout.setOnRefreshListener { presenter.getGenres() }

        tvNoInternet.setOnClickListener { presenter.checkIsNetworkAvailable() }
    }

    private fun initRecView() {
        rvGenre.adapter = genresAdapter
        rvGenre.layoutManager = GridLayoutManager(this, 2)
        rvGenre.itemAnimator = LandingAnimator()
        rvGenre.setItemViewCacheSize(50)
    }

    override fun onPause() {
        super.onPause()
        hideLoading()
    }

    override fun displayGenres(genres: Genres) {
        showRecyclerView()
        genresAdapter.updateGenres(genres)
    }

    override fun showRecyclerView() {
        tvNoInternet.hide()
        rvGenre.show()
    }

    override fun showEmptyStateView() {
        rvGenre.hide()
        tvNoInternet.show()
    }

    override fun showLoading() {
        refreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        refreshLayout.isRefreshing = false
    }

    override fun showNoInternetConnectionError() {
        showLongSnackBar(rvGenre, R.string.no_internet)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unSubscribe()
    }
}
