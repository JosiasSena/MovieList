package com.josiassena.movielist.app

import android.app.Application
import com.josiassena.movielist.app_helpers.dependency_injection.DIComponent
import com.josiassena.movielist.app_helpers.dependency_injection.DaggerDIComponent
import com.rapidsos.dependencyinjection.modules.ApiModule
import com.rapidsos.dependencyinjection.modules.DatabaseModule
import com.rapidsos.dependencyinjection.modules.NetworkManagerModule

/**
 * File created by josiassena on 7/5/17.
 */
class App : Application() {

    companion object {
        @JvmStatic lateinit var component: DIComponent
    }

    override fun onCreate() {
        super.onCreate()
        initObjectGraph()
    }

    private fun initObjectGraph() {
        component = DaggerDIComponent.builder()
                .apiModule(ApiModule(this))
                .networkManagerModule(NetworkManagerModule(this))
                .databaseModule(DatabaseModule(this))
                .build()
    }
}