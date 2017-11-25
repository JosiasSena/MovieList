package com.josiassena.movielist.app_helpers.data_providers.listeners

import com.josiassena.core.Genres

/**
 * @author Josias Sena
 */
interface OnGotGenreListener {
    fun onSuccess(genres: Genres)
    fun onError(message: String)
}