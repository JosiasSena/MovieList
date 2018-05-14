package com.rapidsos.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.josiassena.core.MovieVideosResult
import io.reactivex.Maybe

/**
 * @author Josias Sena
 */
@Dao
interface MovieVideosResultDao : DaoRepository<MovieVideosResult> {

    @Query("SELECT * FROM movie_videos_result WHERE id LIKE :id")
    fun getMoviePreviewsForMovieId(id: Int): Maybe<List<MovieVideosResult>>

    /**
     * Insert a list of results into the database.
     *
     * @param results the result list to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(results: List<MovieVideosResult>)

}