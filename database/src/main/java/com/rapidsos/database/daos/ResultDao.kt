package com.rapidsos.database.daos

import android.arch.persistence.room.*
import com.josiassena.core.Result

/**
 * @author Josias Sena
 */
@Dao
interface ResultDao {

    @Query("SELECT * FROM result")
    fun getAll(): List<Result>

    @Query("SELECT * FROM result WHERE id LIKE :movieId LIMIT 1")
    fun getMovieFromId(movieId: Int): Result

    @Delete
    fun delete(result: Result)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(results: List<Result>)

}