package com.josiassena.movielist.app_helpers.observers

import io.reactivex.disposables.CompositeDisposable

/**
 * @author Josias Sena
 */
object GenreLifeCycleObserver : DisposableLifeCycleObserver() {
    override fun getCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}
