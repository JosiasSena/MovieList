package com.josiassena.movielist.app_helpers.observers.fragment

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.josiassena.movielist.app_helpers.observers.BaseDisposableObserver

/**
 * @author Josias Sena
 */
interface DisposableFragmentLifeCycleObserver : BaseDisposableObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun unSubscribe() {
        getCompositeDisposable().clear()
    }

}