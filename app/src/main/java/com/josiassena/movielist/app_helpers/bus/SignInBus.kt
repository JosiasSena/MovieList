package com.josiassena.movielist.app_helpers.bus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * @author Josias Sena
 */
enum class SignInBus {

    INSTANCE;

    private val bus = PublishSubject.create<Boolean>()

    fun send(isSignedIn: Boolean) {
        bus.onNext(isSignedIn)
    }

    fun toObservable(): Observable<Boolean> {
        return bus
    }

}