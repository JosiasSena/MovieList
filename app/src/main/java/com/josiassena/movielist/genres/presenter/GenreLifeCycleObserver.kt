package com.josiassena.movielist.genres.presenter

import com.josiassena.movielist.app_helpers.observers.DisposableLifeCycleObserver
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Josias Sena
 */
object GenreLifeCycleObserver : DisposableLifeCycleObserver() {
    override fun getCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}
