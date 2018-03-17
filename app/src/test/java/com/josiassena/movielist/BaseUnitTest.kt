package com.josiassena.movielist

import android.content.Context
import com.josiassena.movieapi.di_modules.MovieApiModule
import com.josiassena.movielist.app_helpers.dependency_injection.DIComponent
import com.josiassena.movielist.app_helpers.dependency_injection.modules.ProvidersModule
import com.rapidsos.database.di_modules.DatabaseModule
import com.rapidsos.helpers.network.di_module.NetworkManagerModule
import dagger.Component
import org.mockito.Mockito
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
open class BaseUnitTest {

    lateinit var component: TestComponent

    @Singleton
    @Component(modules = [NetworkManagerModule::class, DatabaseModule::class,
        ProvidersModule::class, MovieApiModule::class])
    interface TestComponent : DIComponent

    @Throws(Exception::class)
    open fun setUp() {
        val context = Mockito.mock(Context::class.java)

        component = DaggerBaseUnitTest_TestComponent.builder()
                .apiModule(ApiTestModule(context))
                .networkManagerModule(NetworkManagerTestModule(context))
                .databaseModule(DatabaseManagerTestModule(context))
                .providersModule(ProvidersTestModule())
                .build()
    }
}