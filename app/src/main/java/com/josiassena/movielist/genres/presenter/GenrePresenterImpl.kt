package com.josiassena.movielist.genres.presenter

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.core.Genres
import com.josiassena.movielist.app.App
import com.josiassena.movielist.app_helpers.data_providers.GenreProvider
import com.josiassena.movielist.genres.view.GenreView
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.helpers.network.NetworkManager
import io.reactivex.MaybeObserver
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class GenrePresenterImpl : MvpBasePresenter<GenreView>(), GenrePresenter, AnkoLogger {

    @Inject
    lateinit var databaseManager: DatabaseManager

    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var genreProvider: GenreProvider

    init {
        App.component.inject(this)

        networkManager.subscribeToCurrentNetworkState(object : Observer<Connectivity> {
            override fun onSubscribe(disposable: Disposable) {
                GenreLifeCycleObserver.getCompositeDisposable().add(disposable)
            }

            override fun onError(throwable: Throwable) {
                error(throwable.message, throwable)
            }

            override fun onNext(connectivity: Connectivity) {
            }

            override fun onComplete() {
            }
        })
    }

    override fun getGenres() {
        showLoading()

        genreProvider.getGenres(object : MaybeObserver<Genres?> {

            override fun onSubscribe(disposable: Disposable) {
                GenreLifeCycleObserver.getCompositeDisposable().add(disposable)
            }

            override fun onSuccess(genres: Genres) {
                hideLoading()

                if (isViewAttached) {
                    view?.displayGenres(genres)
                }
            }

            override fun onError(throwable: Throwable) {
                error(throwable.message, throwable)

                hideLoading()

                if (!isNetworkAvailable()) {
                    view?.showEmptyStateView()
                    view?.showNoInternetConnectionError()
                }
            }

            override fun onComplete() {
            }
        })
    }

    private fun showLoading() {
        if (isViewAttached) {
            view?.showLoading()
        }
    }

    private fun hideLoading() {
        if (isViewAttached) {
            view?.hideLoading()
        }
    }

    override fun isNetworkAvailable() = networkManager.isNetworkAvailable()

    override fun checkIsNetworkAvailable() {
        if (isNetworkAvailable()) {

            if (isViewAttached) {
                view?.showRecyclerView()
                getGenres()
            }

        } else {
            if (isViewAttached) {
                view?.showNoInternetConnectionError()
            }
        }
    }

    override fun getGenresFromQuery(query: String) {
        databaseManager.getGenres()
                .map {
                    it.genres.filter {
                        it.name?.contains(query, ignoreCase = true) as Boolean
                    }
                }
                .subscribe({
                    if (isViewAttached) {
                        view?.displayGenres(Genres(it))
                    }
                }, { throwable: Throwable ->
                    error(throwable.message, throwable)
                })
    }
}