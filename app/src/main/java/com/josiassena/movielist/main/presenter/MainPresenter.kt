package com.josiassena.movielist.main.presenter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.movielist.app_helpers.bus.SignInBus
import com.josiassena.movielist.app_helpers.preferences.MoviesPreferences
import com.josiassena.movielist.main.view.MainView
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class MainPresenter @Inject constructor(private val preferences: MoviesPreferences) :
        MvpBasePresenter<MainView>(), Presenter {

    private val fireBaseAuth by lazy { FirebaseAuth.getInstance() }

    init {
        SignInBus.INSTANCE.toObservable().subscribe { isSignedIn ->
            if (isSignedIn) {
                view?.populateNavDrawerHeader()
            } else {
                view?.resetNavDrawerHeader()
            }
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return fireBaseAuth.currentUser
    }

    override fun isSignedIn(): Boolean = preferences.isSignedIn()
}