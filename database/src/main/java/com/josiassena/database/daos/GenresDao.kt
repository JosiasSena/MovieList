package com.josiassena.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.josiassena.core.Genres
import io.reactivex.Single

/**
 * @author Josias Sena
 */
@Dao
interface GenresDao : DaoRepository<Genres> {

    @Query("SELECT * FROM genres")
    fun getGenres(): Single<Genres>

}