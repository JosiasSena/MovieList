package com.josiassena.movielist.app

import android.app.Application
import com.josiassena.movielist.app_helpers.dependency_injection.DIComponent
import com.josiassena.movielist.app_helpers.dependency_injection.DaggerDIComponent
import com.josiassena.movielist.app_helpers.dependency_injection.modules.ApiModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.DatabaseModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.NetworkManagerModule

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
                .apiModule(ApiModule(this))
                .networkManagerModule(NetworkManagerModule(this))
                .databaseModule(DatabaseModule(this))
                .build()
    }
}