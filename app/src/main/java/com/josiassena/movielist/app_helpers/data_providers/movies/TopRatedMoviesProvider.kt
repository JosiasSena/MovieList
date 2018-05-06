package com.josiassena.movielist.app_helpers.data_providers.movies

import com.josiassena.core.MovieResults
import com.josiassena.movieapi.Api
import com.rapidsos.database.database.DatabaseManager
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class TopRatedMoviesProvider @Inject constructor(private val api: Api,
                                                 private val databaseManager: DatabaseManager) :
        AnkoLogger, ApiErrorFormatter {

    fun getTopRatedMovies(observer: Observer<MovieResults?>) {
        Observable.merge(getTopRatedMoviesNetworkObservable(observer), getTopRatedMoviesDatabaseObservable())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    private fun getTopRatedMoviesNetworkObservable(observer: Observer<MovieResults?>):
            Observable<MovieResults?> {
        return api.getTopRatedMovies()
                .filter({
                    when {
                        it.isSuccessful.and(it.body() != null) -> return@filter true
                        else -> {
                            val throwable = Throwable(getFormattedError(it))
                            observer.onError(throwable)
                            return@filter false
                        }
                    }
                })
                .map { return@map it.body() }
                .doOnNext { it?.let { databaseManager.saveMovieResults(it) } }
    }

    private fun getTopRatedMoviesDatabaseObservable(): Observable<MovieResults> {
        return databaseManager.getTopRatedMovies().toObservable()
    }

}