package com.josiassena.movielist.movie_info.presenter

import android.app.DownloadManager
import android.graphics.Color
import android.support.customtabs.CustomTabsIntent
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.core.MovieVideosResult
import com.josiassena.core.Result
import com.josiassena.movieapi.Api
import com.josiassena.movielist.app.App
import com.josiassena.movielist.movie_info.view.MovieInfoView
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.helpers.network.NetworkManager
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

    init {
        App.component.inject(this)
    }

    companion object {
        private const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
    }

    override fun getMovieFromId(movieId: Int): Maybe<Result> = databaseManager.getMovieFromId(movieId)

    override fun getPreviewsForMovieFromId(movieId: Int) {
        Observable.merge(getMoviesFromDatabaseObservable(movieId), getMoviesFromNetworkObservable(movieId))
                .subscribeOn(Schedulers.io())
                .doOnEach { result -> savePreviewsToDatabase(result.value) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<MovieVideosResult>> {

                    override fun onSubscribe(disposable: Disposable) {
                        compositeDisposable.add(disposable)
                    }

                    override fun onError(throwable: Throwable) {
                        error(throwable.message, throwable)
                    }

                    override fun onNext(result: List<MovieVideosResult>) {
                        if (result.isEmpty()) {
                            if (isViewAttached) {
                                view?.hidePreviews()
                            }
                        } else {
                            if (isViewAttached) {
                                view?.showPreviews(result)
                            }
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
                .collectInto(arrayListOf<MovieVideosResult>(), { list, item ->
                    list.addAll(item)
                }).toObservable()
    }

    private fun getMoviesFromNetworkObservable(movieId: Int):
            Observable<ArrayList<MovieVideosResult>> {
        return api.getMoviePreviewsForMovieId(movieId)
                .filter { response -> response.isSuccessful }
                .map { response -> response.body()?.results }
                .filter { results -> results.isNotEmpty() }
                .collectInto(arrayListOf<MovieVideosResult>(), { list, collector ->
                    collector?.let { list.addAll(it) }
                }).toObservable()
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
}
