package com.rapidsos.helpers.api

import com.josiassena.core.GenreMovieResults
import com.josiassena.core.Genres
import com.josiassena.core.MovieVideos
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Josias Sena
 */
interface Api {

    @GET("genre/movie/list?api_key=0caee56a11a5a429c08fcb2adc042db5")
    fun getMovieGenres(): Observable<Response<Genres>>

    @GET("genre/{genre_id}/movies?api_key=0caee56a11a5a429c08fcb2adc042db5&include_adult=false")
    fun getMovies(@Path("genre_id") genreId: Int): Observable<Response<GenreMovieResults>>

    @GET("genre/{genre_id}/movies?api_key=0caee56a11a5a429c08fcb2adc042db5&include_adult=false")
    fun getMoviesByPage(@Path("genre_id") genreId: Int, @Query("page") page: Int):
            Observable<Response<GenreMovieResults>>

    @GET("movie/{movie_id}/videos?api_key=0caee56a11a5a429c08fcb2adc042db5")
    fun getMoviePreviewsForMovieId(@Path("movie_id") movieId: Int) : Observable<Response<MovieVideos>>

}