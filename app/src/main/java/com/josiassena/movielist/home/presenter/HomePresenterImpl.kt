package com.josiassena.movielist.home.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.core.MovieResults
import com.josiassena.core.Result
import com.josiassena.movieapi.Api
import com.josiassena.movielist.app.App
import com.josiassena.movielist.app_helpers.data_providers.movies.MoviesNowPlayingProvider
import com.josiassena.movielist.app_helpers.data_providers.movies.TopRatedMoviesProvider
import com.josiassena.movielist.app_helpers.data_providers.movies.UpcomingMoviesProvider
import com.josiassena.movielist.app_helpers.observers.fragment.HomeLifeCycleObserver
import com.josiassena.movielist.home.view.HomeView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
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
    lateinit var topRatedMoviesProvider: TopRatedMoviesProvider

    @Inject
    lateinit var upcomingMoviesProvider: UpcomingMoviesProvider

    @Inject
    lateinit var moviesNowPlayingProvider: MoviesNowPlayingProvider

    init {
        App.component.inject(this)
    }

    override fun getTop5RatedMovies() {
        topRatedMoviesProvider.getTopRatedMovies(object : Observer<MovieResults?> {

            override fun onSubscribe(disposable: Disposable) {
                HomeLifeCycleObserver.getCompositeDisposable().add(disposable)
            }

            override fun onError(throwable: Throwable) {
                error(throwable.message, throwable)

                if (isViewAttached) {
                    view?.displayTop5EmptyStateView()
                }
            }

            override fun onNext(topRatedMovies: MovieResults) {
                if (isViewAttached && topRatedMovies.results.isNotEmpty()) {
                    view?.dismissTop5EmptyStateView()

                    val validMovies = topRatedMovies.results.filter {
                        return@filter filterOutMoviesWithoutPoster(it)
                    }

                    val top5 = validMovies.subList(0, 5)
                    view?.onGotTopRatedMovies(top5)
                }
            }

            override fun onComplete() {
            }
        })
    }

    override fun get5UpcomingMovies() {
        upcomingMoviesProvider.getUpcomingMovies(object : Observer<MovieResults?> {

            override fun onSubscribe(disposable: Disposable) {
                HomeLifeCycleObserver.getCompositeDisposable().add(disposable)
            }

            override fun onError(throwable: Throwable) {
                error(throwable.message, throwable)

                if (isViewAttached) {
                    view?.displayUpcomingEmptyStateView()
                }
            }

            override fun onNext(upcomingMovies: MovieResults) {
                if (isViewAttached && upcomingMovies.results.isNotEmpty()) {
                    view?.dismissUpcomingEmptyStateView()

                    val validMovies = upcomingMovies.results.filter {
                        return@filter filterOutMoviesWithoutPoster(it)
                    }

                    val top5 = validMovies.subList(0, 5)
                    view?.onGotUpcomingMovies(top5)
                }
            }

            override fun onComplete() {
            }
        })
    }

    override fun get5NowPlayingMovies() {
        moviesNowPlayingProvider.getMoviesNowPlaying(object : Observer<MovieResults?> {

            override fun onSubscribe(disposable: Disposable) {
                HomeLifeCycleObserver.getCompositeDisposable().add(disposable)
            }

            override fun onError(throwable: Throwable) {
                error(throwable.message, throwable)

                if (isViewAttached) {
                    view?.displayNowPlayingEmptyStateView()
                }
            }

            override fun onNext(nowPlayingMovies: MovieResults) {
                if (isViewAttached && nowPlayingMovies.results.isNotEmpty()) {
                    view?.dismissNowPlayingEmptyStateView()

                    val validMovies = nowPlayingMovies.results.filter {
                        return@filter filterOutMoviesWithoutPoster(it)
                    }

                    val top5 = validMovies.subList(0, 5)
                    view?.onGotNowPlayingMovies(top5)
                }
            }

            override fun onComplete() {
            }
        })
    }

    private fun filterOutMoviesWithoutPoster(it: Result): Boolean {
        return it.posterPath != null && it.posterPath?.isNotEmpty() as Boolean
    }
}