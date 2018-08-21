package com.josiassena.helpers.extensions

import android.view.View

/**
 * @author Josias Sena
 */

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}