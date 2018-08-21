package com.josiassena.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.josiassena.core.MovieResults
import io.reactivex.Maybe

/**
 * @author Josias Sena
 */
@Dao
interface GenreMovieResultsDao : DaoRepository<MovieResults> {

    @Query("SELECT * FROM genre_movie_results WHERE id LIKE :genreId LIMIT 1")
    fun getMoviesForGenreId(genreId: Int?): Maybe<MovieResults>

    @Query("SELECT * FROM genre_movie_results WHERE id LIKE :id AND page LIKE :page LIMIT 1")
    fun getMoviesForGenrePaginated(id: Int?, page: Int?): Maybe<MovieResults>
}