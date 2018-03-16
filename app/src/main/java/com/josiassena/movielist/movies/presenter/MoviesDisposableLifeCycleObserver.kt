package com.josiassena.movielist.movies.presenter

import com.josiassena.movielist.app_helpers.observers.DisposableLifeCycleObserver
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Josias Sena
 */
object MoviesDisposableLifeCycleObserver : DisposableLifeCycleObserver() {
    override fun getCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}