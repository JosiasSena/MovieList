package com.josiassena.movielist.settings.view

import com.google.firebase.auth.FirebaseUser
import com.hannesdorfmann.mosby.mvp.MvpView

/**
 * @author Josias Sena
 */
interface View : MvpView {
    fun showSignInButton()
    fun showSignOutButton()
    fun displayUserData(currentUser: FirebaseUser)
    fun hideUserData()
}