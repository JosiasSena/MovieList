package com.josiassena.movielist.genres.presenter

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.core.Genres
import com.josiassena.movielist.app.App
import com.josiassena.movielist.app_helpers.data_providers.GenreProvider
import com.josiassena.movielist.app_helpers.data_providers.listeners.OnGotGenreListener
import com.josiassena.movielist.app_helpers.listener.SearchObservable
import com.josiassena.movielist.genres.view.GenreView
import com.mancj.materialsearchbar.MaterialSearchBar
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.helpers.network.NetworkManager
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class GenrePresenterImpl : MvpBasePresenter<GenreView>(), GenrePresenter, AnkoLogger {

    private val compositeDisposable = CompositeDisposable()

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
                compositeDisposable.add(disposable)
            }

            override fun onError(e: Throwable) {
                error(e.message, e)
            }

            override fun onNext(connectivity: Connectivity) {
                if (networkManager.isInternetConnectionAvailable(connectivity)) {
                    getGenres()
                } else {
                    if (isViewAttached) {
                        view?.showNoInternetConnectionError()
                    }
                }
            }

            override fun onComplete() {
            }
        })
    }

    override fun getGenres() {
        showLoading()

        genreProvider.getGenres(object : OnGotGenreListener {

            override fun onSuccess(genres: Genres) {
                hideLoading()

                if (isViewAttached) {
                    view?.displayGenres(genres)
                }
            }

            override fun onError(message: String) {
                error(message)

                hideLoading()

                if (!isNetworkAvailable()) {
                    view?.showEmptyStateView()
                    view?.showNoInternetConnectionError()
                }
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

    override fun unSubscribe() = compositeDisposable.clear()

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

    override fun listenToSearchViewChanges(genreSearchView: MaterialSearchBar) {
        SearchObservable.fromView(genreSearchView)
                .subscribeOn(Schedulers.io())
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onError(e: Throwable) {
                        error(e.message, e)
                    }

                    override fun onNext(query: String) {
                        if (query.isEmpty()) {
                            getGenres()
                        } else {
                            getGenresFromQuery(query)
                        }
                    }

                    override fun onComplete() {
                    }
                })
    }

    private fun getGenresFromQuery(query: String) {
        databaseManager.getGenres()
                .map { genres ->
                    genres.genres.filter { genre ->
                        genre.name.contains(query, ignoreCase = true)
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