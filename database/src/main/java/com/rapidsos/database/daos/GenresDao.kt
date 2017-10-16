package com.rapidsos.database.daos

import android.arch.persistence.room.*
import com.josiassena.core.Genres

/**
 * @author Josias Sena
 */
@Dao
interface GenresDao {

    @Query("SELECT * FROM genres")
    fun getGenres(): Genres

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(genres: Genres)

    @Delete
    fun delete(genres: Genres)

}