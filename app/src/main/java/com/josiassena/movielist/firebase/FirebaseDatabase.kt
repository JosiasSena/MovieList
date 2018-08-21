package com.josiassena.movielist.firebase

import javax.inject.Inject

/**
 * @author Josias Sena
 */
class FirebaseDatabase @Inject constructor(private val favoriteMovies: FavoriteMovies) {

    fun getFavoriteMovies(): FavoriteMovies {
        return favoriteMovies
    }
}