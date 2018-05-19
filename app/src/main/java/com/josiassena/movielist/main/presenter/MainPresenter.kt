package com.josiassena.movielist.main.presenter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.movielist.main.view.View

/**
 * @author Josias Sena
 */
class MainPresenter : MvpBasePresenter<View>(), Presenter {

    private val fireBaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun getCurrentUser(): FirebaseUser? {
        return fireBaseAuth.currentUser
    }

}