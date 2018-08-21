package com.josiassena.movielist.firebase

import com.josiassena.movielist.app_helpers.exceptions.MovieException

/**
 * @author Josias Sena
 */
interface OnMovieAddedToFavoritesListener {

    fun onSuccess()
    fun onError(exception: MovieException?)

}