package com.josiassena.movielist.settings.presenter

import com.google.firebase.auth.FirebaseUser
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.josiassena.movielist.settings.view.View

/**
 * @author Josias Sena
 */
internal interface Presenter : MvpPresenter<View> {
    fun updateCurrentUserData(currentUser: FirebaseUser?)
    fun onSignedOut()
    fun getCurrentUser(): FirebaseUser?
}