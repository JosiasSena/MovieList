package com.josiassena.movielist.app_helpers.data_providers

import android.support.annotation.VisibleForTesting
import com.josiassena.core.Genres
import com.josiassena.movieapi.Api
import com.rapidsos.database.database.DatabaseManager
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class GenreProvider @Inject constructor(private val api: Api,
                                        private val databaseManager: DatabaseManager) : AnkoLogger {

    private lateinit var observer: MaybeObserver<Genres?>

    /**
     * Provides a list of genres to the [observer]. The genres comes from either the local
     * database or the web.
     *
     * @param observer the lister to notify on success or error
     */
    fun getGenres(observer: MaybeObserver<Genres?>) {
        this.observer = observer
        Observable.merge(getGenresDatabaseObservable(), getGenresNetworkObservable())
                .subscribeOn(Schedulers.io())
                .firstElement()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    @VisibleForTesting()
    fun getGenresNetworkObservable(): Observable<Genres?> {
        return api.getMovieGenres()
                .filter({ response ->
                    if (response.isSuccessful.and(response.body() != null)) {
                        return@filter true
                    }

                    error("getGenres error: ${response.errorBody()?.string()}")
                    observer.onError(Throwable(response.message()))
                    return@filter false
                })
                .map { return@map it.body() }
                .doOnNext { it?.let { databaseManager.saveGenres(it) } }
    }

    @VisibleForTesting
    fun getGenresDatabaseObservable(): Observable<Genres> = databaseManager.getGenres().toObservable()
}