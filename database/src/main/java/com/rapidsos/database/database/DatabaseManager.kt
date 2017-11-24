package com.rapidsos.database.database

import android.support.annotation.WorkerThread
import com.josiassena.core.GenreMovieResults
import com.josiassena.core.Genres
import com.josiassena.core.MovieVideosResult
import com.josiassena.core.Result
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync

/**
 * @author Josias Sena
 */
class DatabaseManager(private val database: MLDatabase) : AnkoLogger {

    private val genresDao = database.genresDao()
    private val genreMovieResultsDao = database.genreMovieResultsDao()
    private val movieVideosDao = database.movieVideosDao()
    private val movieVideosResultDao = database.movieVideosResultDao()
    private val resultDao = database.resultDao()

    fun saveGenres(genres: Genres) {
        doAsync {
            genresDao.insert(genres)
        }
    }

    fun savePreviews(values: ArrayList<MovieVideosResult>) {
        doAsync {
            database.movieVideosResultDao().insertAll(values)
        }
    }

    fun saveMovieResults(genreMovieResults: GenreMovieResults?) {
        doAsync {
            genreMovieResults?.let {
                genreMovieResultsDao.insert(it)
                resultDao.insert(genreMovieResults.results)
            }
        }
    }

    @WorkerThread
    fun getMovieFromId(movieId: Int): Result {
        return Observable.just(database.resultDao())
                .subscribeOn(Schedulers.io())
                .map { it.getMovieFromId(movieId) }
                .blockingFirst()
    }

    fun getGenres(): Maybe<Genres> {
        return genresDao.getGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    @WorkerThread
    fun getMoviePreviewsForMovieId(movieId: Int): List<MovieVideosResult> {
        return Observable.just(database.movieVideosResultDao())
                .subscribeOn(Schedulers.io())
                .map { it.getMoviePreviewsForMovieId(movieId) }
                .blockingFirst()
    }

    @WorkerThread
    fun getMoviesForGenre(id: Int?): List<GenreMovieResults> {
        return Observable.just(database.genreMovieResultsDao())
                .subscribeOn(Schedulers.io())
                .map { it.getMoviesForGenre(id) }
                .blockingFirst()
    }

    @WorkerThread
    fun getMoviesPaginated(id: Int?, page: Int?): List<GenreMovieResults> {
        return Observable.just(database.genreMovieResultsDao())
                .subscribeOn(Schedulers.io())
                .map { it.getMoviesForGenrePaginated(id, page) }
                .blockingFirst()
    }
}