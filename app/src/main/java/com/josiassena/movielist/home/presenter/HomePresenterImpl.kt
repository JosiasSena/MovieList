package com.josiassena.movielist.home.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.core.MovieResults
import com.josiassena.movieapi.Api
import com.josiassena.movielist.app.App
import com.josiassena.movielist.app_helpers.data_providers.MovieProvider
import com.josiassena.movielist.app_helpers.observers.HomeLifeCycleObserver
import com.josiassena.movielist.home.view.HomeView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class HomePresenterImpl : MvpBasePresenter<HomeView>(), HomePresenter, AnkoLogger {

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var movieProvider: MovieProvider

    init {
        App.component.inject(this)
    }

    override fun getTopRatedMovies() {
        movieProvider.getTopRatedMovies(object : Observer<MovieResults?> {

            override fun onSubscribe(disposable: Disposable) {
                HomeLifeCycleObserver.getCompositeDisposable().add(disposable)
            }

            override fun onError(throwable: Throwable) {
                error(throwable.message, throwable)
            }

            override fun onNext(topRatedMovies: MovieResults) {
                if (isViewAttached) {
                    val top5 = topRatedMovies.results.subList(0, 5)
                    view?.onGotTopRatedMovies(top5)
                }
            }

            override fun onComplete() {
            }
        })
    }

    override fun getNowPlayingMovies() {
        api.getMoviesNowPlaying()
                .subscribeOn(Schedulers.io())
                .filter {
                    if (it.isSuccessful) {
                        return@filter true
                    }

                    error("Error: ${it.message()}: \n ${it.errorBody()?.string()}")
                    return@filter false
                }
                .map {
                    it.body()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MovieResults?> {

                    override fun onSubscribe(disposable: Disposable) {
                        HomeLifeCycleObserver.getCompositeDisposable().add(disposable)
                    }

                    override fun onError(throwable: Throwable) {
                        error(throwable.message, throwable)
                    }

                    override fun onNext(nowPlayingMovies: MovieResults) {
                        if (isViewAttached) {
                            val top5 = nowPlayingMovies.results.subList(0, 5)
                            view?.onGotNowPlayingMovies(top5)
                        }
                    }

                    override fun onComplete() {
                    }
                })
    }

    override fun getUpcomingMovies() {
        api.getUpcomingMovies()
                .subscribeOn(Schedulers.io())
                .filter {
                    if (it.isSuccessful) {
                        return@filter true
                    }

                    error("Error: ${it.message()}: \n ${it.errorBody()?.string()}")
                    return@filter false
                }
                .map {
                    it.body()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MovieResults?> {

                    override fun onSubscribe(disposable: Disposable) {
                        HomeLifeCycleObserver.getCompositeDisposable().add(disposable)
                    }

                    override fun onError(throwable: Throwable) {
                        error(throwable.message, throwable)
                    }

                    override fun onNext(upcomingMovies: MovieResults) {
                        if (isViewAttached) {
                            val top5 = upcomingMovies.results.subList(0, 5)
                            view?.onGotUpcomingMovies(top5)
                        }
                    }

                    override fun onComplete() {
                    }
                })
    }
}