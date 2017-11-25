package com.josiassena.movielist.app_helpers.data_providers

import com.josiassena.core.Genres
import com.josiassena.movielist.app_helpers.data_providers.listeners.OnGotGenreListener
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.helpers.api.Api
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class GenreProvider @Inject constructor(private val api: Api,
                                        private val databaseManager: DatabaseManager) : AnkoLogger {

    /**
     * Provides a list of genres to the [listener]. The genres comes from either the local
     * database or the web.
     *
     * @param listener the lister to notify on success or error
     */
    fun getGenres(listener: OnGotGenreListener) {
        val database = databaseManager.getGenres().toObservable()

        val network = api.getMovieGenres()
                .filter({ response ->
                    if (response.isSuccessful.and(response.body() != null)) {
                        return@filter true
                    }

                    error("getGenres error: ${response.errorBody()?.string()}")
                    return@filter false
                })
                .map { return@map it.body() }
                .doOnNext { genres: Genres? ->
                    genres?.let {
                        databaseManager.saveGenres(it)
                    }
                }

        Observable.merge(database, network)
                .subscribeOn(Schedulers.io())
                .firstElement()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : MaybeObserver<Genres?> {
                    private lateinit var genreDisposable: Disposable

                    override fun onSubscribe(disposable: Disposable) {
                        this.genreDisposable = disposable
                    }

                    override fun onSuccess(genres: Genres) {
                        listener.onSuccess(genres)
                    }

                    override fun onError(throwable: Throwable) {
                        listener.onError(throwable.message.toString())
                    }

                    override fun onComplete() {
                        genreDisposable.dispose()
                    }
                })
    }
}