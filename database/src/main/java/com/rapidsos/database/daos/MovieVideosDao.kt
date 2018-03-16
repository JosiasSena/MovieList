package com.rapidsos.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.josiassena.core.MovieVideos

/**
 * @author Josias Sena
 */
@Dao
interface MovieVideosDao : DaoRepository<MovieVideos> {

    private companion object {
        private const val TABLE_NAME = "movie_videos"
    }

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): List<MovieVideos>

}