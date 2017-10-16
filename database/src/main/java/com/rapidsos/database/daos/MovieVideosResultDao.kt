package com.rapidsos.database.daos

import android.arch.persistence.room.*
import com.josiassena.core.MovieVideosResult

/**
 * @author Josias Sena
 */
@Dao
interface MovieVideosResultDao {

    @Query("SELECT * FROM movie_videos_result WHERE id LIKE :id")
    fun getMoviePreviewsForMovieId(id: Int): List<MovieVideosResult>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieVideosResult: List<MovieVideosResult>)

    @Delete
    fun delete(movieVideosResult: MovieVideosResult)

}