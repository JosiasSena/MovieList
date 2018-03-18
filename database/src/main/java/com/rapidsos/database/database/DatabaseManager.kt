package com.rapidsos.database.database

import com.josiassena.core.GenreMovieResults
import com.josiassena.core.Genres
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

    fun saveMovieResults(genreMovieResults: GenreMovieResults?) {
        doAsync {
            genreMovieResults?.let {
                genreMovieResultsDao.insert(it)
                resultDao.insertAll(genreMovieResults.results)
            }
        }
    }

    fun getMovieFromId(movieId: Int): Maybe<Result> {
        return database.resultDao()
                .getMovieFromId(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

    fun getMoviePreviewsForMovieId(movieId: Int): Maybe<List<MovieVideosResult>> {
        return movieVideosResultDao
                .getMoviePreviewsForMovieId(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMoviesForGenreId(id: Int?): Maybe<GenreMovieResults> {
        return database.genreMovieResultsDao()
                .getMoviesForGenreId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMoviesPaginated(id: Int?, page: Int?): Maybe<GenreMovieResults> {
        return database.genreMovieResultsDao()
                .getMoviesForGenrePaginated(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}