package com.josiassena.movielist.firebase

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.josiassena.movielist.app_helpers.exceptions.MovieException
import java.lang.Exception
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class FavoriteMovies @Inject constructor(private val firebaseFirestore: FirebaseFirestore) {

    private val favoritesCollection by lazy {
        firebaseFirestore.collection(FAVORITE_MOVIES_COLLECTION)
    }

    companion object {
        private const val TAG = "FirebaseDatabase"
        private const val FAVORITES = "favorites"
        private const val FAVORITE_MOVIES_COLLECTION = "favorite_movies"
    }

    fun getAll(onCompletionListener: OnCompleteListener<DocumentSnapshot>) {
        Log.d(TAG, "Getting all favorite movies")

        firebaseFirestore.app.uid?.let {
            favoritesCollection
                    .document(it)
                    .get()
                    .addOnCompleteListener(onCompletionListener)
                    .addOnFailureListener { exception: Exception ->
                        Log.d(TAG, "Error getting favorite movies ${exception.message}", exception)
                    }
        }
    }

    fun add(movieId: Long, listener: OnMovieAddedToFavoritesListener) {
        Log.d(TAG, "Adding movie to favorites: $movieId")

        val userId = firebaseFirestore.app.uid

        if (userId == null) {
            listener.onError(MovieException("Must be logged in to add movie to favorites"))
        } else {
            val favoritesMap: HashMap<String, Any> = hashMapOf()

            getAll(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val favorites = task.result.data?.get(FAVORITES) as List<Long>?

                    if (favorites != null && !favorites.isEmpty()) {
                        if (favorites.contains(movieId)) {
                            Log.d(TAG, "Movie is already in favorites. Not adding.")
                        } else {
                            val mutableFavorites = favorites.toMutableList()
                            mutableFavorites.add(movieId)

                            favoritesMap[FAVORITES] = mutableFavorites
                            addMovieToFavorites(userId, favoritesMap, listener)
                        }
                    } else {
                        favoritesMap[FAVORITES] = arrayListOf(movieId)
                        addMovieToFavorites(userId, favoritesMap, listener)
                    }
                } else {
                    listener.onError(MovieException(task.exception?.message as String))
                }
            })
        }
    }

    private fun addMovieToFavorites(userId: String, favoritesMap: HashMap<String, Any>,
                                    listener: OnMovieAddedToFavoritesListener) {
        favoritesCollection.document(userId)
                .set(favoritesMap)
                .addOnSuccessListener {
                    listener.onSuccess()
                }.addOnFailureListener { exception: Exception ->
                    listener.onError(MovieException(exception.message.toString()))
                }
    }

    fun remove(movieId: Long, listener: OnMovieRemovedFromFavoritesListener) {
        Log.d(TAG, "Removing movie from favorites: $movieId")

        val userId = firebaseFirestore.app.uid

        getAll(OnCompleteListener { task ->
            if (task.isSuccessful) {
                val favorites = task.result.data?.get(FAVORITES) as List<Long>?

                if (favorites != null && !favorites.isEmpty()) {
                    if (favorites.contains(movieId)) {
                        val mutableFavorites = favorites.toMutableList()
                        mutableFavorites.remove(movieId)

                        val favoritesMap: HashMap<String, Any> = hashMapOf(FAVORITES to mutableFavorites)

                        userId?.let { id: String ->
                            favoritesCollection.document(id)
                                    .update(favoritesMap)
                                    .addOnSuccessListener {
                                        listener.onSuccess()
                                    }.addOnFailureListener { exception: Exception ->
                                        listener.onError(MovieException(exception.message.toString()))
                                    }
                        }
                    } else {
                        listener.onError(MovieException("Movie not in favorites."))
                    }
                } else {
                    listener.onError(MovieException("No favorite movies available."))
                }
            } else {
                listener.onError(MovieException(task.exception?.message.toString()))
            }
        })
    }
}