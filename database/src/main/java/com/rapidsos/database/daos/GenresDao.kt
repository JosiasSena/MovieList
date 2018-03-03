package com.rapidsos.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.josiassena.core.Genres
import io.reactivex.Single

/**
 * @author Josias Sena
 */
@Dao
interface GenresDao : DaoRepository<Genres> {

    private companion object {
        private const val TABLE_NAME = "genres"
    }

    @Query("SELECT * FROM $TABLE_NAME")
    fun getGenres(): Single<Genres>

}