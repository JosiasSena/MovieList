package com.rapidsos.database.database

import com.josiassena.core.Genres
import com.josiassena.core.MovieResults
import com.josiassena.core.MovieVideosResult
import com.josiassena.core.Result
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.warn
import java.util.*

/**
 * @author Josias Sena
 */
open class DatabaseManager(private val database: MLDatabase) : AnkoLogger {

    private val genresDao = database.genresDao()
    private val genreMovieResultsDao = database.genreMovieResultsDao()
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

    fun saveMovieResults(movieResults: MovieResults?) {
        doAsync {
            movieResults?.let {
                genreMovieResultsDao.insert(it)
                resultDao.insertAll(movieResults.results)
            }
        }
    }

    fun getGenres(): Single<Genres> {
        return genresDao.getGenres()
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext { throwable: Throwable ->
                    SingleSource {
                        warn(throwable.message, throwable)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMovieFromId(movieId: Int): Maybe<Result> {
        return database.resultDao()
                .getMovieFromId(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMoviePreviewsForMovieId(movieId: Int): Maybe<List<MovieVideosResult>> {
        return movieVideosResultDao
                .getMoviePreviewsForMovieId(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMoviesForGenreId(id: Int?): Maybe<MovieResults> {
        return database.genreMovieResultsDao()
                .getMoviesForGenreId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMoviesPaginated(id: Int?, page: Int?): Maybe<MovieResults> {
        return database.genreMovieResultsDao()
                .getMoviesForGenrePaginated(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTopRatedMovies(): Maybe<MovieResults> {
        return database.resultDao()
                .getTopRatedMovies()
                .map { return@map MovieResults().apply { this.results = it } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUpcomingMovies(): Maybe<MovieResults> {
        return database.resultDao()
                .getUpcomingMovies()
                .map { return@map MovieResults().apply { this.results = it } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMoviesNowPlaying(): Maybe<MovieResults> {
        return database.resultDao()
                .getMoviesNowPlaying()
                .map { return@map MovieResults().apply { this.results = it } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}