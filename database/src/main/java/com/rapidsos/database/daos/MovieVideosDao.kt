package com.rapidsos.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Query
import com.josiassena.core.MovieVideos

/**
 * @author Josias Sena
 */
@Dao
interface MovieVideosDao {

    @Query("SELECT * FROM movie_videos")
    fun getAll(): List<MovieVideos>

    @Delete
    fun delete(movieVideo: MovieVideos)

}