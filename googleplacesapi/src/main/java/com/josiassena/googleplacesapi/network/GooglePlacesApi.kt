package com.josiassena.googleplacesapi.network

import com.josiassena.googleplacesapi.models.PlaceResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Josias Sena
 */
interface GooglePlacesApi {

    @GET("maps/api/place/nearbysearch/json?radius=3000&type=movie_theater&keyword=movie,theater,cinema")
    fun getMovieTheatersNearby(
            @Query("location", encoded = true) latitudeLongitude: String,
            @Query("key") apiKey: String
    ): Observable<Response<PlaceResponse>>
}