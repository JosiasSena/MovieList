package com.josiassena.movielist.app_helpers.data_providers

import com.josiassena.core.MovieResults
import com.josiassena.movieapi.Api
import com.rapidsos.database.database.DatabaseManager
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import retrofit2.Response
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class MovieProvider @Inject constructor(private val api: Api,
                                        private val databaseManager: DatabaseManager) : AnkoLogger {

    //region Get movies normally
    /**
     * Provides a list of movies to the [observer]. The movies comes from either the local
     * database or the web.
     *
     * @param genreId the genre id to use to fetch the movies
     */
    fun getMovies(genreId: Int, observer: MaybeObserver<MovieResults?>) {
        Observable.merge(getMoviesDatabaseObservable(genreId), getMoviesNetworkObservable(genreId, observer))
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
                            observer.onError(Throwable(getFormattedError(it)))
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
    fun getMoviesPaginated(genreId: Int, page: Int, observer: MaybeObserver<MovieResults?>) {
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
                            observer.onError(Throwable(getFormattedError(it)))
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

    private fun getFormattedError(response: Response<MovieResults>): String {
        return "${response.message()}: \n ${response.errorBody()?.string()}"
    }

    //region Get top rated movies
    fun getTopRatedMovies(observer: Observer<MovieResults?>) {
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
                .subscribe(observer)
    }
    //endregion
}