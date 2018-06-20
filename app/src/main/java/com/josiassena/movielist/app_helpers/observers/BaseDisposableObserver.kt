package com.josiassena.movielist.app_helpers.observers

import android.arch.lifecycle.LifecycleObserver
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Josias Sena
 */
interface BaseDisposableObserver : LifecycleObserver {
    fun getCompositeDisposable(): CompositeDisposable
}