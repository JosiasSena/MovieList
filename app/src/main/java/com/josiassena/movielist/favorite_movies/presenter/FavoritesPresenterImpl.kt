package com.josiassena.movielist.favorite_movies.presenter

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.movielist.app.App
import com.josiassena.movielist.app_helpers.preferences.MoviesPreferences
import com.josiassena.movielist.favorite_movies.view.FavoritesView
import com.josiassena.movielist.firebase.FirebaseDatabase
import com.rapidsos.database.database.DatabaseManager
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class FavoritesPresenterImpl : MvpBasePresenter<FavoritesView>(), FavoritesPresenter {

    @Inject
    lateinit var preferences: MoviesPreferences

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase

    @Inject
    lateinit var databaseManager: DatabaseManager

    companion object {
        private const val TAG = "FavoritesPresenterImpl"
    }

    init {
        App.component.inject(this)
    }

    override fun getFavoriteMovies() {

        if (preferences.isSignedIn()) {
            firebaseDatabase.getFavoriteMovies()
                    .getAll(OnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val favoritesIds = task.result.data?.get("favorites") as List<Long>?

                            if (favoritesIds != null && !favoritesIds.isEmpty()) {
                                // TODO convert movie ids to movie objects and pass into the view to display
                                // view?.displayFavoriteMovies(favoritesIds)
                            } else {
                                if (isViewAttached) {
                                    view?.showEmptyListView()
                                }
                            }
                        } else {
                            Log.e(TAG, "Error getting favorite movies.", task.exception)
                        }
                    })

        } else {
            if (isViewAttached) {
                view?.goToLogInScreen()
            }
        }
    }
}