package com.josiassena.movielist.app_helpers.preferences

import android.content.SharedPreferences
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class MoviesPreferences @Inject constructor(private val preferences: SharedPreferences) {

    companion object {
        const val IS_SIGNED_IN = "is_signed_in"
    }

    fun setIsSignedIn(isSignedIn: Boolean) {
        preferences.edit().putBoolean(IS_SIGNED_IN, isSignedIn).apply()
    }

    fun isSignedIn(): Boolean = preferences.getBoolean(IS_SIGNED_IN, false)

}