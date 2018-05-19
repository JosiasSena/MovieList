package com.josiassena.movielist.settings.view

import com.hannesdorfmann.mosby.mvp.MvpView

/**
 * @author Josias Sena
 */
interface View : MvpView {
    fun showSignInButton()
    fun showSignOutButton()
}