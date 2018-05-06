package com.josiassena.movielist.app_helpers.data_providers.movies

import com.josiassena.core.MovieResults
import retrofit2.Response

/**
 * @author Josias Sena
 */
internal interface ApiErrorFormatter {

    fun getFormattedError(response: Response<MovieResults>): String {
        return "${response.message()}: \n ${response.errorBody()?.string()}"
    }

}