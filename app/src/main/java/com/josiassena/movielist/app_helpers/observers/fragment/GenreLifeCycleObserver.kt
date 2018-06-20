package com.josiassena.movielist.app_helpers.observers.fragment

import io.reactivex.disposables.CompositeDisposable

/**
 * @author Josias Sena
 */
object GenreLifeCycleObserver : DisposableFragmentLifeCycleObserver {

    private val disposable by lazy { CompositeDisposable() }

    override fun getCompositeDisposable(): CompositeDisposable = disposable
}
