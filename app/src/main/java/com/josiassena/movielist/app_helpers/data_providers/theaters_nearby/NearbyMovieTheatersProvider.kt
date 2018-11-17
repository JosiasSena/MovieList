package com.josiassena.movielist.app_helpers.data_providers.theaters_nearby

import android.location.Location
import com.josiassena.googleplacesapi.models.PlaceResponse
import com.josiassena.googleplacesapi.network.GooglePlacesApi
import com.josiassena.movielist.app_helpers.data_providers.movies.ApiErrorFormatter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * @author Josias Sena
 */
class NearbyMovieTheatersProvider(private val googlePlacesApi: GooglePlacesApi) : ApiErrorFormatter {

    fun getMovieTheaters(apiKey: String, location: Location, observer: Observer<Response<PlaceResponse>>) {
        val latitudeLongitudeQuery = "${location.latitude},${location.longitude}"

        googlePlacesApi.getMovieTheatersNearby(latitudeLongitudeQuery, apiKey)
                .subscribeOn(Schedulers.io())
                .filter {
                    when {
                        it.isSuccessful.and(it.body() != null) -> return@filter true
                        else -> {
                            val throwable = Throwable(getFormattedError(it))
                            observer.onError(throwable)
                            return@filter false
                        }
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }
}