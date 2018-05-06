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

    companion object {
        private const val DEFAULT_QUERY = "&region=US&include_adult=false"
    }

    @GET("genre/movie/list?api_key=${BuildConfig.MOVIE_DB_API_KEY}")
    fun getMovieGenres(): Observable<Response<Genres>>

    @GET("genre/{genre_id}/movies?api_key=${BuildConfig.MOVIE_DB_API_KEY}$DEFAULT_QUERY")
    fun getMovies(@Path("genre_id") genreId: Int): Observable<Response<MovieResults>>

    @GET("genre/{genre_id}/movies?api_key=${BuildConfig.MOVIE_DB_API_KEY}$DEFAULT_QUERY")
    fun getMoviesByPage(@Path("genre_id") genreId: Int,
                        @Query("page") page: Int): Observable<Response<MovieResults>>

    @GET("movie/{movie_id}/videos?api_key=${BuildConfig.MOVIE_DB_API_KEY}$DEFAULT_QUERY")
    fun getMoviePreviewsForMovieId(@Path("movie_id") movieId: Int): Observable<Response<MovieVideos>>

    @GET("movie/top_rated?api_key=${BuildConfig.MOVIE_DB_API_KEY}$DEFAULT_QUERY")
    fun getTopRatedMovies(): Observable<Response<MovieResults>>

    @GET("movie/now_playing?api_key=${BuildConfig.MOVIE_DB_API_KEY}$DEFAULT_QUERY")
    fun getMoviesNowPlaying(): Observable<Response<MovieResults>>

    @GET("movie/upcoming?api_key=${BuildConfig.MOVIE_DB_API_KEY}$DEFAULT_QUERY")
    fun getUpcomingMovies(): Observable<Response<MovieResults>>

}