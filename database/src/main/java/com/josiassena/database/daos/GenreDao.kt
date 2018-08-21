package com.josiassena.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.josiassena.core.Genre
import io.reactivex.Single

/**
 * @author Josias Sena
 */
@Dao
interface GenreDao : DaoRepository<Genre> {

    @Query("SELECT * FROM genre")
    fun getGenre(): Single<Genre>

}