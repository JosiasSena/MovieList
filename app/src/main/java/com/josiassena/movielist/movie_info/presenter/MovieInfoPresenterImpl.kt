package com.josiassena.movielist.movie_info.presenter

import android.graphics.Color
import android.support.customtabs.CustomTabsIntent
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.core.MovieVideosResult
import com.josiassena.movielist.app.App
import com.josiassena.movielist.movie_info.view.MovieInfoView
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.helpers.api.Api
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class MovieInfoPresenterImpl : MvpBasePresenter<MovieInfoView>(), MovieInfoPresenter, AnkoLogger {

    private val youtubeBaseUrl = "https://www.youtube.com/watch?v="
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var databaseManager: DatabaseManager

    init {
        App.component.inject(this)
    }

    override fun getMovieFromId(movieId: Int) = databaseManager.getMovieFromId(movieId)

    override fun getPreviewsForMovieFromId(movieId: Int) {

        val database = databaseManager.getMoviePreviewsForMovieId(movieId).toObservable()
                .collectInto(arrayListOf<MovieVideosResult>(), { list, item ->
                    list.add(item)
                }).toObservable()

        val network = api.getMoviePreviewsForMovieId(movieId)
                .filter { response -> response.isSuccessful }
                .map { response -> response.body()?.results }
                .filter { results -> results.isNotEmpty() }
                .collectInto(arrayListOf<MovieVideosResult>(), { list, collector ->
                    collector?.let { list.addAll(it) }
                }).toObservable()

        Observable.merge(database, network)
                .subscribeOn(Schedulers.io())
                .doOnEach { result -> savePreviewsToDatabase(result.value) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<MovieVideosResult>> {

                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
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

    override fun unSubscribe() = compositeDisposable.clear()

    private fun savePreviewsToDatabase(previewsList: ArrayList<MovieVideosResult>?) {
        previewsList?.let { databaseManager.savePreviews(previewsList) }
    }

    override fun playVideoFromPreview(preview: MovieVideosResult) {
        if (isViewAttached) {
            view?.playVideo(youtubeBaseUrl + preview.key)
        }
    }

    override fun getCustomTabsIntent(): CustomTabsIntent = CustomTabsIntent.Builder()
            .setToolbarColor(Color.RED)
            .setShowTitle(true)
            .build()
}
