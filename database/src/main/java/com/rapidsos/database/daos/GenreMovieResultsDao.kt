package com.rapidsos.database.daos

import android.arch.persistence.room.*
import com.josiassena.core.GenreMovieResults

/**
 * @author Josias Sena
 */
@Dao
interface GenreMovieResultsDao {

    @Query("SELECT * FROM genre_movie_results")
    fun getAll(): List<GenreMovieResults>

    @Query("SELECT * FROM genre_movie_results WHERE id LIKE :id")
    fun getMoviesForGenre(id: Int?): List<GenreMovieResults>

    @Query("SELECT * FROM genre_movie_results WHERE id LIKE :id AND page LIKE :page LIMIT 1")
    fun getMoviesForGenrePaginated(id: Int?, page: Int?): List<GenreMovieResults>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(genreMovieResults: GenreMovieResults)

    @Delete
    fun delete(genreMovieResults: GenreMovieResults)
}