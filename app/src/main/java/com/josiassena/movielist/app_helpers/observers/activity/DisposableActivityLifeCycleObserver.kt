package com.josiassena.movielist.app_helpers.observers.activity

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.josiassena.movielist.app_helpers.observers.BaseDisposableObserver

/**
 * @author Josias Sena
 */
abstract class DisposableActivityLifeCycleObserver : BaseDisposableObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unSubscribe() {
        getCompositeDisposable().clear()
    }

}