package com.rapidsos.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.josiassena.core.GenreMovieResults
import io.reactivex.Maybe

/**
 * @author Josias Sena
 */
@Dao
interface GenreMovieResultsDao : DaoRepository<GenreMovieResults> {

    private companion object {
        private const val TABLE_NAME = "genre_movie_results"
    }

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): List<GenreMovieResults>

    @Query("SELECT * FROM $TABLE_NAME WHERE id LIKE :id")
    fun getMoviesForGenre(id: Int?): Maybe<List<GenreMovieResults>>

    @Query("SELECT * FROM $TABLE_NAME WHERE id LIKE :id AND page LIKE :page LIMIT 1")
    fun getMoviesForGenrePaginated(id: Int?, page: Int?): Maybe<List<GenreMovieResults>>
}