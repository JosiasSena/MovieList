package com.josiassena.movielist.movies.presenter

import com.josiassena.movielist.app_helpers.observers.activity.DisposableActivityLifeCycleObserver
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Josias Sena
 */
object MoviesDisposableLifeCycleObserver : DisposableActivityLifeCycleObserver() {
    override fun getCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}