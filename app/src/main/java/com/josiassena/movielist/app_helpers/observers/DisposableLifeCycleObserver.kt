package com.josiassena.movielist.app_helpers.observers

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Josias Sena
 */
abstract class DisposableLifeCycleObserver : LifecycleObserver {

    abstract fun getCompositeDisposable(): CompositeDisposable

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unSubscribe() {
        getCompositeDisposable().clear()
    }

}