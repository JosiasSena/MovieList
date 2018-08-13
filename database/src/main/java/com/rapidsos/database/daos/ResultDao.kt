package com.rapidsos.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.josiassena.core.Result
import io.reactivex.Maybe

/**
 * @author Josias Sena
 */
@Dao
interface ResultDao : DaoRepository<Result> {

    @Query("SELECT * FROM result")
    fun getAll(): Maybe<List<Result>>

    @Query("SELECT * FROM result WHERE id LIKE :movieId LIMIT 1")
    fun getMovieFromId(movieId: Int): Maybe<Result>

    @Query("SELECT * FROM result ORDER BY vote_average DESC")
    fun getTopRatedMovies(): Maybe<List<Result>>

    @Query("SELECT * FROM result WHERE release_date >= date('now')")
    fun getUpcomingMovies(): Maybe<List<Result>>

    @Query("SELECT * FROM result WHERE release_date BETWEEN date('now', '-2 months') AND date('now') ORDER BY release_date DESC")
    fun getMoviesNowPlaying(): Maybe<List<Result>>

    /**
     * Insert a list of results into the database.
     *
     * @param results the result list to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(results: List<Result>)

}