package com.josiassena.movielist

import android.content.Context
import com.josiassena.movielist.app_helpers.dependency_injection.DIComponent
import com.josiassena.movielist.genres.presenter.GenrePresenterImplTest
import com.josiassena.movielist.genres.view.GenreActivityTest
import com.rapidsos.dependencyinjection.modules.ApiModule
import com.rapidsos.dependencyinjection.modules.DatabaseModule
import com.rapidsos.dependencyinjection.modules.NetworkManagerModule
import dagger.Component
import org.mockito.Mockito
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
open class BaseUnitTest {

    lateinit var component: TestComponent

    @Singleton
    @Component(modules = arrayOf(ApiModule::class, NetworkManagerModule::class,
            DatabaseModule::class))
    interface TestComponent : DIComponent {
        fun inject(genrePresenterImplTest: GenrePresenterImplTest)
        fun inject(genreActivityTest: GenreActivityTest)
    }

    @Throws(Exception::class)
    open fun setUp() {
        val context = Mockito.mock(Context::class.java)

        component = DaggerBaseUnitTest_TestComponent.builder()
                .apiModule(ApiTestModule(context))
                .netWorkManagerModule(NetworkManagerTestModule(context))
                .databaseModule(DatabaseManagerTestModule(context))
                .build()
    }
}