package com.josiassena.movielist.app_helpers.data_providers

import com.josiassena.core.GenreMovieResults
import com.josiassena.movieapi.Api
import com.rapidsos.database.database.DatabaseManager
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
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
    fun getMovies(genreId: Int, observer: MaybeObserver<GenreMovieResults?>) {
        Observable.merge(getMoviesDatabaseObservable(genreId), getMoviesNetworkObservable(genreId, observer))
                .subscribeOn(Schedulers.io())
                .firstElement()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    private fun getMoviesNetworkObservable(genreId: Int, observer: MaybeObserver<GenreMovieResults?>):
            Observable<GenreMovieResults?> {
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

    private fun getMoviesDatabaseObservable(genreId: Int): Observable<GenreMovieResults> {
        return databaseManager.getMoviesForGenreId(genreId).toObservable()
    }
    //endregion

    //region Get movies paginated
    fun getMoviesPaginated(genreId: Int, page: Int, observer: MaybeObserver<GenreMovieResults?>) {
        Observable.merge(getMoviesPaginatedDatabaseObservable(genreId, page),
                getMoviesPaginatedNetworkObservable(genreId, page, observer))
                .subscribeOn(Schedulers.io())
                .firstElement()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }


    private fun getMoviesPaginatedNetworkObservable(genreId: Int, page: Int,
                                                    observer: MaybeObserver<GenreMovieResults?>):
            Observable<GenreMovieResults?> {
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
            Observable<GenreMovieResults> {
        return databaseManager.getMoviesPaginated(genreId, page).toObservable()
    }
    //endregion

    private fun getFormattedError(response: Response<GenreMovieResults>): String {
        return "${response.message()}: \n ${response.errorBody()?.string()}"
    }
}