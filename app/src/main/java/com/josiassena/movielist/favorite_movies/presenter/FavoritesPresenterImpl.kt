package com.josiassena.movielist.favorite_movies.presenter

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.core.Result
import com.josiassena.database.database.DatabaseManager
import com.josiassena.movielist.app.App
import com.josiassena.movielist.app_helpers.preferences.MoviesPreferences
import com.josiassena.movielist.favorite_movies.view.FavoritesView
import com.josiassena.movielist.firebase.FirebaseDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
            firebaseDatabase.getFavoriteMovies().getAll(OnCompleteListener { task ->
                if (task.isSuccessful) {

                    val favoritesIds = task.result.data?.get("favorites") as List<Long>?

                    if (favoritesIds != null && !favoritesIds.isEmpty()) {
                        displayFavoriteMovies(favoritesIds)
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

    private fun displayFavoriteMovies(favoritesIds: List<Long>?) {
        val movies = arrayListOf<Result>()

        Observable.fromArray(favoritesIds)
                .subscribeOn(Schedulers.io())
                .flatMapIterable { ids -> ids }
                .flatMap { id ->
                    databaseManager.getMovieFromId(id.toInt()).toObservable()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movies.add(it)
                }, {
                    Log.e(TAG, "getFavoriteMovies() : ${it.message}", it)
                }, {
                    view?.showFavoriteMovies(movies)
                })
    }
}