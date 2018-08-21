package com.josiassena.movielist.firebase

import com.josiassena.movielist.app_helpers.exceptions.MovieException

/**
 * @author Josias Sena
 */
interface OnMovieRemovedFromFavoritesListener {

    fun onSuccess()
    fun onError(exception: MovieException?)

}