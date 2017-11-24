package com.rapidsos.database.daos

import android.arch.persistence.room.*
import com.josiassena.core.Result
import io.reactivex.Maybe

/**
 * @author Josias Sena
 */
@Dao
interface ResultDao {

    @Query("SELECT * FROM result")
    fun getAll(): Maybe<List<Result>>

    @Query("SELECT * FROM result WHERE id LIKE :movieId LIMIT 1")
    fun getMovieFromId(movieId: Int): Maybe<Result>

    @Delete
    fun delete(result: Result)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(results: List<Result>)

}