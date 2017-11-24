package com.rapidsos.database.daos

import android.arch.persistence.room.*
import com.josiassena.core.MovieVideosResult
import io.reactivex.Maybe

/**
 * @author Josias Sena
 */
@Dao
interface MovieVideosResultDao {

    @Query("SELECT * FROM movie_videos_result WHERE id LIKE :id")
    fun getMoviePreviewsForMovieId(id: Int): Maybe<List<MovieVideosResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieVideosResult: List<MovieVideosResult>)

    @Delete
    fun delete(movieVideosResult: MovieVideosResult)

}