package com.josiassena.movieapi

import com.josiassena.core.Genres
import com.josiassena.core.MovieResults
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

    @GET("genre/movie/list?api_key=${BuildConfig.MOVIE_DB_API_KEY}")
    fun getMovieGenres(): Observable<Response<Genres>>

    @GET("genre/{genre_id}/movies?api_key=${BuildConfig.MOVIE_DB_API_KEY}&include_adult=false")
    fun getMovies(@Path("genre_id") genreId: Int): Observable<Response<MovieResults>>

    @GET("genre/{genre_id}/movies?api_key=${BuildConfig.MOVIE_DB_API_KEY}&include_adult=false")
    fun getMoviesByPage(@Path("genre_id") genreId: Int,
                        @Query("page") page: Int): Observable<Response<MovieResults>>

    @GET("movie/{movie_id}/videos?api_key=${BuildConfig.MOVIE_DB_API_KEY}")
    fun getMoviePreviewsForMovieId(@Path("movie_id") movieId: Int): Observable<Response<MovieVideos>>

    @GET("movie/top_rated?api_key=${BuildConfig.MOVIE_DB_API_KEY}")
    fun getTopRatedMovies(): Observable<Response<MovieResults>>

    @GET("movie/now_playing?api_key=${BuildConfig.MOVIE_DB_API_KEY}")
    fun getMoviesNowPlaying(): Observable<Response<MovieResults>>

    @GET("movie/upcoming?api_key=${BuildConfig.MOVIE_DB_API_KEY}&region=US")
    fun getUpcomingMovies(): Observable<Response<MovieResults>>

}