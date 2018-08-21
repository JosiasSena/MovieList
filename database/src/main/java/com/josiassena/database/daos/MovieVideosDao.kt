package com.josiassena.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.josiassena.core.MovieVideos

/**
 * @author Josias Sena
 */
@Dao
interface MovieVideosDao : DaoRepository<MovieVideos> {

    @Query("SELECT * FROM movie_videos")
    fun getAll(): List<MovieVideos>

}