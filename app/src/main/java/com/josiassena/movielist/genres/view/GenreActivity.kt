package com.josiassena.movielist.genres.view

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.josiassena.core.Genres
import com.josiassena.movielist.R
import com.josiassena.movielist.app.App
import com.josiassena.movielist.app_helpers.listener.SearchObservable
import com.josiassena.movielist.genres.presenter.GenreLifeCycleObserver
import com.josiassena.movielist.genres.presenter.GenrePresenterImpl
import com.josiassena.movielist.genres.view.rec_view.GenresAdapter
import com.rapidsos.helpers.extensions.hide
import com.rapidsos.helpers.extensions.show
import com.rapidsos.helpers.extensions.showLongSnackBar
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.android.synthetic.main.activity_genre.*
import kotlinx.android.synthetic.main.content_genre.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.util.concurrent.TimeUnit

class GenreActivity : MvpActivity<GenreView, GenrePresenterImpl>(), GenreView, AnkoLogger {

    private val genresAdapter = GenresAdapter()

    private var genresRetrieved: Genres? = null
    private var isShouldExecuteGenreSearchQuery = false

    companion object {
        private const val GENRES_KEY = "genres_key"
    }

    override fun createPresenter() = GenrePresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre)

        lifecycle.addObserver(GenreLifeCycleObserver)

        initRecView()

        savedInstanceState?.let { genresRetrieved = it.getParcelable(GENRES_KEY) }

        getGenres()

        refreshLayout.setOnRefreshListener { presenter.getGenres() }

        tvNoInternet.setOnClickListener { presenter.checkIsNetworkAvailable() }

        listenToSearchViewChanges()
    }

    private fun getGenres() {
        if (genresRetrieved != null) {
            showRecyclerView()

            genresRetrieved?.let {
                genresAdapter.updateGenres(it)
            }
        } else {
            presenter.getGenres()
        }
    }

    private fun listenToSearchViewChanges() {
        SearchObservable.fromView(genreSearchView)
                .subscribeOn(Schedulers.io())
                .filter {
                    if (isShouldExecuteGenreSearchQuery) {
                        return@filter true
                    } else {
                        isShouldExecuteGenreSearchQuery = true
                        return@filter false
                    }
                }
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(disposable: Disposable) {
                        GenreLifeCycleObserver.compositeDisposable.add(disposable)
                    }

                    override fun onError(throwable: Throwable) {
                        error(throwable.message, throwable)
                    }

                    override fun onNext(query: String) {
                        presenter.getGenresFromQuery(query)
                    }

                    override fun onComplete() {
                    }
                })
    }

    private fun initRecView() {
        rvGenre.adapter = genresAdapter
        rvGenre.layoutManager = GridLayoutManager(this, 2)
        rvGenre.itemAnimator = LandingAnimator()
        rvGenre.setItemViewCacheSize(50)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable(GENRES_KEY, genresRetrieved)
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        hideLoading()
    }

    override fun displayGenres(genres: Genres) {
        genresRetrieved = genres

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
}
