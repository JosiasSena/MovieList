package com.josiassena.movielist.app

import android.app.Application
import com.josiassena.movieapi.di_modules.MovieApiModule
import com.josiassena.movielist.app_helpers.dependency_injection.DIComponent
import com.josiassena.movielist.app_helpers.dependency_injection.DaggerDIComponent
import com.josiassena.movielist.app_helpers.dependency_injection.modules.AndroidServicesModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.FirebaseModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.MoviesPreferenceModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.ProvidersModule
import com.rapidsos.database.di_modules.DatabaseModule
import com.rapidsos.helpers.network.di_module.NetworkManagerModule

/**
 * File created by josiassena on 7/5/17.
 */
class App : Application() {

    companion object {
        lateinit var component: DIComponent
    }

    override fun onCreate() {
        super.onCreate()
        initObjectGraph()
    }

    private fun initObjectGraph() {
        component = DaggerDIComponent.builder()
                .movieApiModule(MovieApiModule(this))
                .networkManagerModule(NetworkManagerModule(this))
                .databaseModule(DatabaseModule(this))
                .providersModule(ProvidersModule())
                .androidServicesModule(AndroidServicesModule(this))
                .moviesPreferenceModule(MoviesPreferenceModule(this))
                .firebaseModule(FirebaseModule())
                .build()
    }
}