package com.josiassena.movielist.movie_info.presenter

import android.app.DownloadManager
import android.graphics.Color
import android.support.customtabs.CustomTabsIntent
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.core.MovieVideosResult
import com.josiassena.core.Result
import com.josiassena.movieapi.Api
import com.josiassena.movielist.app.App
import com.josiassena.movielist.app_helpers.exceptions.MovieException
import com.josiassena.movielist.app_helpers.preferences.MoviesPreferences
import com.josiassena.movielist.firebase.FirebaseDatabase
import com.josiassena.movielist.firebase.OnMovieAddedToFavoritesListener
import com.josiassena.movielist.firebase.OnMovieRemovedFromFavoritesListener
import com.josiassena.movielist.movie_info.view.MovieInfoView
import com.josiassena.database.database.DatabaseManager
import com.josiassena.helpers.network.NetworkManager
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class MovieInfoPresenterImpl : MvpBasePresenter<MovieInfoView>(), MovieInfoPresenter, AnkoLogger {

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var databaseManager: DatabaseManager

    @Inject
    lateinit var downloadManager: DownloadManager

    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var preferences: MoviesPreferences

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase

    init {
        App.component.inject(this)

        if (preferences.isSignedIn()) {
            if (isViewAttached) {
                view?.showAddToFavorites()
            }
        } else {
            if (isViewAttached) {
                view?.hideAddToFavorites()
            }
        }
    }

    companion object {
        private const val TAG = "MovieInfoPresenterImpl"
        private const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
    }

    override fun getMovieFromId(movieId: Int): Maybe<Result> = databaseManager.getMovieFromId(movieId)

    override fun getPreviewsForMovieFromId(movieId: Int) {
        Observable.merge(getMoviesFromDatabaseObservable(movieId), getMoviesFromNetworkObservable(movieId))
                .subscribeOn(Schedulers.io())
                .filter { !it.isEmpty() }
                .doOnEach { savePreviewsToDatabase(it.value) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<MovieVideosResult>> {

                    override fun onSubscribe(disposable: Disposable) {
                        compositeDisposable.add(disposable)
                    }

                    override fun onError(throwable: Throwable) {
                        error(throwable.message, throwable)
                    }

                    override fun onNext(result: List<MovieVideosResult>) {
                        if (isViewAttached) {
                            view?.showPreviews(result)
                        }
                    }

                    override fun onComplete() {
                        // do nothing
                    }
                })
    }

    private fun getMoviesFromDatabaseObservable(movieId: Int):
            Observable<ArrayList<MovieVideosResult>> {
        return databaseManager.getMoviePreviewsForMovieId(movieId).toObservable()
                .collectInto(arrayListOf<MovieVideosResult>()) { list, item ->
                    list.addAll(item)
                }.toObservable()
    }

    private fun getMoviesFromNetworkObservable(movieId: Int):
            Observable<ArrayList<MovieVideosResult>> {
        return api.getMoviePreviewsForMovieId(movieId)
                .filter { response -> response.isSuccessful }
                .map { response -> response.body()?.results }
                .filter { results -> results.isNotEmpty() }
                .collectInto(arrayListOf<MovieVideosResult>()) { list, collector ->
                    collector?.let { list.addAll(it) }
                }.toObservable()
    }

    override fun unSubscribe() = compositeDisposable.clear()

    private fun savePreviewsToDatabase(previewsList: ArrayList<MovieVideosResult>?) {
        previewsList?.let { databaseManager.savePreviews(previewsList) }
    }

    override fun playVideoFromPreview(preview: MovieVideosResult) {
        if (isViewAttached) {
            view?.playVideo(YOUTUBE_BASE_URL + preview.key)
        }
    }

    override fun getCustomTabsIntent(): CustomTabsIntent {
        return CustomTabsIntent.Builder()
                .setToolbarColor(Color.RED)
                .setShowTitle(true)
                .build()
    }

    override fun downloadMoviePoster(request: DownloadManager.Request) {
        if (networkManager.isInternetConnectionAvailable()) {
            downloadManager.enqueue(request)
        } else {
            if (isViewAttached) {
                view?.showNoInternetConnectionError()
            }
        }
    }

    override fun checkIfIsFavoriteMovie(movieId: Int) {
        firebaseDatabase.getFavoriteMovies()
                .getAll(OnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val favorites = task.result.data?.get("favorites") as List<Long>?

                        if (favorites != null && !favorites.isEmpty()) {
                            if (favorites.contains(movieId.toLong())) {
                                if (isViewAttached) {
                                    view?.showMovieIsFavoriteView()
                                }
                            } else {
                                if (isViewAttached) {
                                    view?.showMovieIsNotFavoriteView()
                                }
                            }
                        } else {
                            if (isViewAttached) {
                                view?.showMovieIsNotFavoriteView()
                            }
                        }
                    } else {
                        Log.e(TAG, "Error getting favorite movies.", task.exception)
                    }
                })
    }

    override fun addMovieToFavorites(movieId: Int) {
        firebaseDatabase.getFavoriteMovies()
                .add(movieId.toLong(), object : OnMovieAddedToFavoritesListener {

                    override fun onSuccess() {
                        Log.d(TAG, "Added movie to favorites successfully")

                        if (isViewAttached) {
                            view?.showMovieIsFavoriteView()
                        }
                    }

                    override fun onError(exception: MovieException?) {
                        Log.e(TAG, "Error adding movie to favorites", exception)
                    }
                })
    }

    override fun removeMovieFromFavorites(movieId: Int) {
        firebaseDatabase.getFavoriteMovies()
                .remove(movieId.toLong(), object : OnMovieRemovedFromFavoritesListener {

                    override fun onSuccess() {
                        Log.d(TAG, "Removed movie from favorites successfully")

                        if (isViewAttached) {
                            view?.showMovieIsNotFavoriteView()
                        }
                    }

                    override fun onError(exception: MovieException?) {
                        Log.e(TAG, "Error removing movie from favorites", exception)
                    }
                })
    }
}
