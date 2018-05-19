package com.josiassena.movielist.app_helpers.dependency_injection.modules

import android.content.Context
import android.content.SharedPreferences
import com.josiassena.movielist.app_helpers.preferences.MoviesPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
@Module
open class MoviesPreferenceModule(private val context: Context) {

    companion object {
        private const val SHARED_PREFERENCES_NAME = "movies_prefs"
    }

    @Provides
    @Singleton
    fun providesSharedPreference(): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesMoviesPreference(sharedPreferences: SharedPreferences): MoviesPreferences {
        return MoviesPreferences(sharedPreferences)
    }

}