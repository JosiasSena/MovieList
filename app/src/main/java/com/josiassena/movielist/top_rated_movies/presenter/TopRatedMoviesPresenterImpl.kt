package com.josiassena.movielist.top_rated_movies.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.core.MovieResults
import com.josiassena.movieapi.Api
import com.josiassena.movielist.app.App
import com.josiassena.movielist.app_helpers.data_providers.movies.MovieProvider
import com.josiassena.movielist.top_rated_movies.view.TopRatedMoviesView
import com.rapidsos.database.database.DatabaseManager
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
class TopRatedMoviesPresenterImpl : MvpBasePresenter<TopRatedMoviesView>(),
        TopRatedMoviesPresenter, AnkoLogger {

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var databaseManager: DatabaseManager

    @Inject
    lateinit var movieProvider: MovieProvider

    init {
        App.component.inject(this)
    }

    override fun getTopRatedMovies() {
        api.getTopRatedMovies()
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
                    }

                    override fun onError(throwable: Throwable) {
                        error(throwable.message, throwable)
                    }

                    override fun onNext(topRatedMovies: MovieResults) {
                        error("SUCCESS $topRatedMovies")
                    }

                    override fun onComplete() {
                    }
                })
    }

}