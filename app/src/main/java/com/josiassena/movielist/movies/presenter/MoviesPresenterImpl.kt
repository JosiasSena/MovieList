package com.josiassena.movielist.movies.presenter

import android.util.Log
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.core.GenreMovieResults
import com.josiassena.movieapi.Api
import com.josiassena.movielist.app.App
import com.josiassena.movielist.movies.view.MoviesView
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.helpers.network.NetworkManager
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class MoviesPresenterImpl : MvpBasePresenter<MoviesView>(), MoviesPresenter, AnkoLogger {

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var databaseManager: DatabaseManager

    companion object {
        private val TAG = MoviesPresenterImpl::class.java.simpleName
    }

    init {
        App.component.inject(this)

        listenToNetworkChanges()
    }

    private fun listenToNetworkChanges() {
        networkManager.subscribeToCurrentNetworkState(object : Observer<Connectivity> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                error(e.message, e)
            }

            override fun onNext(connectivity: Connectivity) {
                if (networkManager.isInternetConnectionAvailable(connectivity)) {
                    if (isViewAttached) {
                        view?.refreshMovies()
                    }
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

    override fun getMoviesForGenreId(id: Int) {
        if (isViewAttached) {
            view?.showLoading()
        }

        api.getMovies(id)
                .subscribeOn(Schedulers.io())
                .filter(Predicate { response ->
                    when {
                        response.isSuccessful -> return@Predicate true
                        else -> {
                            Log.e(TAG, "getMoviesForGenreId: ${response.errorBody()?.string()}")

                            if (isViewAttached) {
                                view?.hideLoading()
                            }

                            return@Predicate false
                        }
                    }
                })
                .map { response -> response.body() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GenreMovieResults?> {

                    override fun onSubscribe(disposable: Disposable) {
                        MoviesDisposableLifeCycleObserver.getCompositeDisposable().add(disposable)
                    }

                    override fun onError(throwable: Throwable) {
                        checkDatabaseForMovies(id)

                        if (isViewAttached) {
                            view?.hideLoading()
                        }

                        error(throwable.message, throwable)
                    }

                    override fun onNext(results: GenreMovieResults) {
                        saveMovieListToDatabase(results)

                        if (isViewAttached) {
                            view?.hideLoading()
                            view?.displayMovies(results)
                        }
                    }

                    override fun onComplete() {
                        // do nothing
                    }
                })
    }

    override fun checkDatabaseForMovies(id: Int?) {
        databaseManager.getMoviesForGenre(id).subscribe { moviesResults ->
            if (moviesResults.isEmpty()) {
                if (!networkManager.isNetworkAvailable() && isViewAttached) {
                    view?.showEmptyStateView()
                    view?.showNoInternetConnectionError()
                }
            } else {
                if (isViewAttached) {
                    moviesResults.forEach {
                        view?.displayMovies(it)
                    }
                }
            }
        }
    }

    override fun saveMovieListToDatabase(results: GenreMovieResults?) {
        databaseManager.saveMovieResults(results)
    }

    override fun getMoreMoviesForGenreId(id: Int, page: Int) {
        if (isViewAttached) {
            view?.showLoading()
        }

        api.getMoviesByPage(id, page)
                .subscribeOn(Schedulers.io())
                .filter(Predicate { response ->
                    when {
                        response.isSuccessful -> return@Predicate true
                        else -> {
                            Log.e(TAG, "getMoreMoviesForGenreId: ${response.errorBody()?.string()}")

                            if (isViewAttached) {
                                view?.hideLoading()
                            }

                            return@Predicate false
                        }
                    }
                })
                .map { response -> response.body() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GenreMovieResults?> {

                    override fun onSubscribe(disposable: Disposable) {
                        MoviesDisposableLifeCycleObserver.getCompositeDisposable().add(disposable)
                    }

                    override fun onError(throwable: Throwable) {
                        checkRealmForMoviesPaginated(id, page)

                        error(throwable.message, throwable)

                        if (isViewAttached) {
                            view?.hideLoading()
                        }
                    }

                    override fun onNext(results: GenreMovieResults) {
                        saveMovieListToDatabase(results)

                        if (isViewAttached) {
                            view?.hideLoading()
                            view?.addMoreMovies(results)
                        }
                    }

                    override fun onComplete() {
                        // do nothing
                    }
                })
    }

    override fun checkRealmForMoviesPaginated(id: Int, page: Int) {
        databaseManager.getMoviesPaginated(id, page).subscribe { movies ->
            if (isViewAttached) {
                if (!networkManager.isNetworkAvailable()) {
                    view?.showNoInternetConnectionError()
                } else {
                    movies.forEach {
                        view?.displayMovies(it)
                    }
                }
            }
        }
    }
}
