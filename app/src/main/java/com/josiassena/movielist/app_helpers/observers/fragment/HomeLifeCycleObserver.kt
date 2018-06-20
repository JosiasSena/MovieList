package com.josiassena.movielist.app_helpers.observers.fragment

import io.reactivex.disposables.CompositeDisposable

/**
 * @author Josias Sena
 */
object HomeLifeCycleObserver : DisposableFragmentLifeCycleObserver {

    private val disposable by lazy { CompositeDisposable() }

    override fun getCompositeDisposable(): CompositeDisposable = disposable
}
