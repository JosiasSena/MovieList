package com.josiassena.movielist.main.presenter

import com.google.firebase.auth.FirebaseUser
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.josiassena.movielist.main.view.MainView

/**
 * @author Josias Sena
 */
internal interface Presenter : MvpPresenter<MainView> {
    fun getCurrentUser(): FirebaseUser?
    fun isSignedIn(): Boolean
}