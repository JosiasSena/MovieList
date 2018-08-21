package com.josiassena.movielist.app_helpers.data_providers.movies

import com.josiassena.core.MovieResults
import com.josiassena.movieapi.Api
import com.josiassena.database.database.DatabaseManager
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class MovieProvider @Inject constructor(private val api: Api,
                                        private val databaseManager: DatabaseManager) : AnkoLogger,
        ApiErrorFormatter {

    //region Get movies normally
    /**
     * Provides a list of movies to the [observer]. The movies comes from either the local
     * database or the web.
     *
     * @param genreId The genre id to use to fetch the movies
     * @param observer The observer to notify of success or error
     */
    fun getMovies(genreId: Int = -1, observer: MaybeObserver<MovieResults?>) {
        Observable.merge(getMoviesDatabaseObservable(genreId),
                getMoviesNetworkObservable(genreId, observer))
                .subscribeOn(Schedulers.io())
                .firstElement()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    private fun getMoviesNetworkObservable(genreId: Int, observer: MaybeObserver<MovieResults?>):
            Observable<MovieResults?> {
        return api.getMovies(genreId)
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

    private fun getMoviesDatabaseObservable(genreId: Int): Observable<MovieResults> {
        return databaseManager.getMoviesForGenreId(genreId).toObservable()
    }
    //endregion

    //region Get movies paginated

    /**
     * Provides a list of movies paginated to the [observer]. The movies comes from either the local
     * database or the web.
     *
     * @param genreId The genre id to use to fetch the movies
     * @param page The page to get
     * @param observer The observer to notify of success or error
     */
    fun getMoviesPaginated(genreId: Int = -1, page: Int, observer: MaybeObserver<MovieResults?>) {
        Observable.merge(getMoviesPaginatedDatabaseObservable(genreId, page),
                getMoviesPaginatedNetworkObservable(genreId, page, observer))
                .subscribeOn(Schedulers.io())
                .firstElement()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }


    private fun getMoviesPaginatedNetworkObservable(genreId: Int, page: Int,
                                                    observer: MaybeObserver<MovieResults?>):
            Observable<MovieResults?> {
        return api.getMoviesByPage(genreId, page)
                .subscribeOn(Schedulers.io())
                .filter(Predicate {
                    when {
                        it.isSuccessful -> return@Predicate true
                        else -> {
                            val throwable = Throwable(getFormattedError(it))
                            observer.onError(throwable)
                            return@Predicate false
                        }
                    }
                })
                .map { response -> response.body() }
                .doOnNext { it?.let { databaseManager.saveMovieResults(it) } }
    }

    private fun getMoviesPaginatedDatabaseObservable(genreId: Int, page: Int):
            Observable<MovieResults> {
        return databaseManager.getMoviesPaginated(genreId, page).toObservable()
    }
    //endregion
}