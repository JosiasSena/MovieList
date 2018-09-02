package com.josiassena.movielist.settings.presenter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.movielist.app_helpers.bus.SignInBus
import com.josiassena.movielist.app_helpers.preferences.MoviesPreferences
import com.josiassena.movielist.settings.view.View
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class SettingsPresenter @Inject constructor(private val preferences: MoviesPreferences) :
        MvpBasePresenter<View>(), Presenter, AnkoLogger {

    private val fireBaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun updateCurrentUserData(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            preferences.setIsSignedIn(true)

            SignInBus.INSTANCE.send(true)

            if (isViewAttached) {
                view?.displayUserData(currentUser)
                view?.showSignOutButton()
            }
        }
    }

    override fun onSignedOut() {
        preferences.setIsSignedIn(false)

        SignInBus.INSTANCE.send(false)

        if (isViewAttached) {
            view?.showSignInButton()
            view?.hideUserData()
        }
    }

    override fun getCurrentUser(): FirebaseUser? = fireBaseAuth.currentUser

}