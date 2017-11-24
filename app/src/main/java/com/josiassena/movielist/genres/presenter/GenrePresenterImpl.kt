package com.josiassena.movielist.genres.presenter

import android.util.Log
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.core.Genre
import com.josiassena.core.Genres
import com.josiassena.movielist.app.App
import com.josiassena.movielist.app_helpers.listener.SearchObservable
import com.josiassena.movielist.genres.view.GenreView
import com.mancj.materialsearchbar.MaterialSearchBar
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.helpers.api.Api
import com.rapidsos.helpers.network.NetworkManager
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class GenrePresenterImpl : MvpBasePresenter<GenreView>(), GenrePresenter, AnkoLogger {

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var databaseManager: DatabaseManager

    @Inject
    lateinit var networkManager: NetworkManager

    companion object {
        private val TAG = GenrePresenterImpl::class.java.simpleName
    }

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

        val database = databaseManager.getGenresAsObservable()

        val network = api.getMovieGenres()
                .filter({ response ->
                    if (response.isSuccessful.and(response.body() != null)) {
                        return@filter true
                    }

                    Log.e(TAG, "getGenres filter error: ${response.errorBody()?.string()}")
                    return@filter false
                })
                .map { response: Response<Genres> -> response.body() }

        Observable.merge(database, network)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Genres?> {
                    private lateinit var genreDisposable: Disposable

                    override fun onSubscribe(disposable: Disposable) {
                        this.genreDisposable = disposable
                        compositeDisposable.add(disposable)
                    }

                    override fun onNext(genres: Genres) {
                        databaseManager.saveGenres(genres)

                        if (isViewAttached) {
                            view?.displayGenres(genres)
                        }
                    }

                    override fun onError(throwable: Throwable) {
                        Log.e(TAG, "getGenres onError: ${throwable.message}", throwable)

                        val genres: Genres? = databaseManager.getGenres()

                        if (genres != null) {
                            view?.displayGenres(genres)
                        } else {
                            if (!isNetworkAvailable()) {
                                view?.showEmptyStateView()
                                view?.showNoInternetConnectionError()
                            }
                        }
                    }

                    override fun onComplete() {
                        compositeDisposable.remove(genreDisposable)
                        hideLoading()
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

    override fun queryGenres(query: String): List<Genre>? {
        return databaseManager.getGenres()?.genres?.filter { genre ->
            genre.name.contains(query, ignoreCase = true)
        }
    }

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
                            queryGenres(query)?.let {
                                if (isViewAttached) {
                                    view?.displayGenres(Genres(it))
                                }
                            }
                        }
                    }

                    override fun onComplete() {
                    }
                })
    }
}