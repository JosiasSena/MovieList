package com.rapidsos.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.josiassena.core.Genre
import io.reactivex.Single

/**
 * @author Josias Sena
 */
@Dao
interface GenreDao : DaoRepository<Genre> {

    private companion object {
        private const val TABLE_NAME = "genre"
    }

    @Query("SELECT * FROM $TABLE_NAME")
    fun getGenre(): Single<Genre>

}