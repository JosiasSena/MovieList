package com.rapidsos.database.daos

import android.arch.persistence.room.*
import com.josiassena.core.Genre

/**
 * @author Josias Sena
 */
@Dao
interface GenreDao {

    @Query("SELECT * FROM genre")
    fun getAll(): List<Genre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genre: List<Genre>)

    @Delete
    fun delete(genre: Genre)

}