package com.josiassena.movielist.main.view

import com.hannesdorfmann.mosby.mvp.MvpView

/**
 * @author Josias Sena
 */
interface MainView : MvpView {
    fun populateNavDrawerHeader()
    fun resetNavDrawerHeader()
}