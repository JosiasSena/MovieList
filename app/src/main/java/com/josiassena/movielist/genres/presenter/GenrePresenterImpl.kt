package com.josiassena.movielist.genres.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.core.Genres
import com.josiassena.movielist.app.App
import com.josiassena.movielist.app_helpers.data_providers.genre.GenreProvider
import com.josiassena.movielist.app_helpers.observers.fragment.GenreLifeCycleObserver
import com.josiassena.movielist.genres.view.GenreView
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.helpers.network.NetworkManager
import io.reactivex.MaybeObserver
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

    private fun isNetworkAvailable() = networkManager.isInternetConnectionAvailable()

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