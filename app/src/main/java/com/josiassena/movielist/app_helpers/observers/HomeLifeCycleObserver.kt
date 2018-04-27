package com.josiassena.movielist.app_helpers.observers

import io.reactivex.disposables.CompositeDisposable

/**
 * @author Josias Sena
 */
object HomeLifeCycleObserver : DisposableLifeCycleObserver() {
    override fun getCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}
