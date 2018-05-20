package com.josiassena.movielist.settings.presenter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
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
            if (isViewAttached) {
                view?.showSignOutButton()
            }
        }
    }

    override fun onSignedOut() {
        if (isViewAttached) {
            view?.showSignInButton()
        }
    }

    override fun getCurrentUser(): FirebaseUser? = fireBaseAuth.currentUser

}