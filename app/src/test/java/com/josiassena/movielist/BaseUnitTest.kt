package com.josiassena.movielist

import android.content.Context
import com.josiassena.movielist.app_helpers.dependency_injection.DIComponent
import com.josiassena.movielist.app_helpers.dependency_injection.modules.ApiModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.DatabaseModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.NetworkManagerModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.ProvidersModule
import dagger.Component
import org.mockito.Mockito
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
open class BaseUnitTest {

    lateinit var component: TestComponent

    @Singleton
    @Component(modules = arrayOf(ApiModule::class, NetworkManagerModule::class, DatabaseModule::class,
            ProvidersModule::class))
    interface TestComponent : DIComponent {
        fun inject(genreProviderTest: GenreProviderTest)
    }

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