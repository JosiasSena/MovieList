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
class MoviesNowPlayingProvider @Inject constructor(private val api: Api,
                                                   private val databaseManager: DatabaseManager)
    : AnkoLogger, ApiErrorFormatter {

    fun getMoviesNowPlaying(observer: Observer<MovieResults?>) {
        val apiObservable = getMoviesNowPlayingNetworkObservable(observer)
        val databaseObservable = getMoviesNowPlayingDatabaseObservable()

        Observable.merge(apiObservable, databaseObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    private fun getMoviesNowPlayingNetworkObservable(observer: Observer<MovieResults?>):
            Observable<MovieResults?>? {
        return api.getMoviesNowPlaying()
                .subscribeOn(Schedulers.io())
                .filter {
                    if (it.isSuccessful) {
                        return@filter true
                    }

                    val throwable = Throwable(getFormattedError(it))
                    observer.onError(throwable)
                    return@filter false
                }
                .map {
                    it.body()
                }
                .doOnNext { it?.let { databaseManager.saveMovieResults(it) } }
    }

    private fun getMoviesNowPlayingDatabaseObservable(): Observable<MovieResults> {
        return databaseManager.getMoviesNowPlaying().toObservable()
    }

}