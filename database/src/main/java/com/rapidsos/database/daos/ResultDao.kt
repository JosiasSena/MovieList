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

    private companion object {
        private const val TABLE_NAME = "result"
    }

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): Maybe<List<Result>>

    @Query("SELECT * FROM $TABLE_NAME WHERE id LIKE :movieId LIMIT 1")
    fun getMovieFromId(movieId: Int): Maybe<Result>

    /**
     * Insert a list of results into the database.
     *
     * @param results the result list to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(results: List<Result>)

}