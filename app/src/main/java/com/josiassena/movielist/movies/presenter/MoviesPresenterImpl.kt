package com.josiassena.movielist.movies.presenter

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.core.GenreMovieResults
import com.josiassena.movieapi.Api
import com.josiassena.movielist.app.App
import com.josiassena.movielist.app_helpers.data_providers.MovieProvider
import com.josiassena.movielist.movies.view.MoviesView
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
class MoviesPresenterImpl : MvpBasePresenter<MoviesView>(), MoviesPresenter, AnkoLogger {

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var movieProvider: MovieProvider

    init {
        App.component.inject(this)

        listenToNetworkChanges()
    }

    private fun listenToNetworkChanges() {
        networkManager.subscribeToCurrentNetworkState(object : Observer<Connectivity> {
            override fun onSubscribe(d: Disposable) {
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

    override fun getMoviesForGenreId(genreId: Int) {
        if (isViewAttached) {
            view?.showLoading()
        }

        movieProvider.getMovies(genreId, object : MaybeObserver<GenreMovieResults?> {

            override fun onSubscribe(disposable: Disposable) {
                MoviesDisposableLifeCycleObserver.getCompositeDisposable().add(disposable)
            }

            override fun onError(throwable: Throwable) {
                error(throwable.message, throwable)

                if (isViewAttached) {
                    view?.hideLoading()
                }
            }

            override fun onSuccess(genreMovieResults: GenreMovieResults) {
                if (isViewAttached) {
                    view?.hideLoading()

                    if (genreMovieResults.results.isEmpty()) {
                        if (networkManager.isNetworkAvailable()) {
                            view?.showEmptyStateView()
                        } else {
                            view?.showNoInternetConnectionError()
                        }
                    } else {
                        view?.displayMovies(genreMovieResults)
                    }
                }
            }

            override fun onComplete() {
            }
        })
    }

    override fun getMoreMoviesForGenreId(genreId: Int, page: Int) {
        if (isViewAttached) {
            view?.showLoading()
        }

        movieProvider.getMoviesPaginated(genreId, page, object : MaybeObserver<GenreMovieResults?> {

            override fun onSubscribe(disposable: Disposable) {
                MoviesDisposableLifeCycleObserver.getCompositeDisposable().add(disposable)
            }

            override fun onError(throwable: Throwable) {
                error(throwable.message, throwable)

                if (isViewAttached) {
                    view?.hideLoading()
                }
            }

            override fun onSuccess(genreMovieResults: GenreMovieResults) {

                if (isViewAttached) {
                    view?.hideLoading()

                    if (genreMovieResults.results.isEmpty()) {
                        if (networkManager.isNetworkAvailable()) {
                            view?.showEmptyStateView()
                        } else {
                            view?.showNoInternetConnectionError()
                        }
                    } else {
                        view?.addMoreMovies(genreMovieResults)
                    }
                }
            }

            override fun onComplete() {
            }
        })
    }
}
