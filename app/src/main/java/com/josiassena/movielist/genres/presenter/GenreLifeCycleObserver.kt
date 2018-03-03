package com.josiassena.movielist.genres.presenter

import com.josiassena.movielist.app_helpers.interfaces.DisposableLifeCycleObserver
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Josias Sena
 */
object GenreLifeCycleObserver : DisposableLifeCycleObserver {

    override var compositeDisposable: CompositeDisposable = CompositeDisposable()

}
