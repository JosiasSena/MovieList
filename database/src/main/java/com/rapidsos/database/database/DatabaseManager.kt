package com.rapidsos.database.database

import android.arch.persistence.room.Room
import android.content.Context
import android.support.annotation.WorkerThread
import com.josiassena.core.GenreMovieResults
import com.josiassena.core.Genres
import com.josiassena.core.MovieVideosResult
import com.josiassena.core.Result
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync

/**
 * @author Josias Sena
 */
class DatabaseManager(context: Context) : AnkoLogger {

    private var database: MLDatabase =
            Room.databaseBuilder(context, MLDatabase::class.java, "movie_list_db")
                    .fallbackToDestructiveMigration()
                    .build()

    fun savePreviews(values: ArrayList<MovieVideosResult>) {
        doAsync {
            database.movieVideosResultDao().insertAll(values)
        }
    }

    fun saveMovieResults(genreMovieResults: GenreMovieResults?) {
        doAsync {
            genreMovieResults?.let {
                database.genreMovieResultsDao().insert(it)
                database.resultDao().insert(genreMovieResults.results)
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

    @WorkerThread
    fun getGenres(): Genres? {
        return Observable.just(database.genresDao())
                .subscribeOn(Schedulers.io())
                .map { it.getGenres() }
                .blockingFirst()
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