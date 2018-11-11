package com.josiassena.movielist.app

import android.app.Application
import com.josiassena.database.di_modules.DatabaseModule
import com.josiassena.helpers.network.di_module.NetworkManagerModule
import com.josiassena.movieapi.di_modules.MovieApiModule
import com.josiassena.movielist.app_helpers.dependency_injection.DIComponent
import com.josiassena.movielist.app_helpers.dependency_injection.DaggerDIComponent
import com.josiassena.movielist.app_helpers.dependency_injection.modules.AndroidServicesModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.FirebaseModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.MoviesPreferenceModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.ProvidersModule
import com.squareup.leakcanary.LeakCanary

/**
 * File created by josiassena on 7/5/17.
 */
class App : Application() {

    companion object {
        lateinit var component: DIComponent
    }

    override fun onCreate() {
        super.onCreate()

        initLeakCanary()

        initObjectGraph()

    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.install(this)
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