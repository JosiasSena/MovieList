package com.josiassena.movielist.app_helpers.dependency_injection.modules

import android.content.Context
import com.rapidsos.helpers.api.ApiManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
@Module
open class ApiModule(private val context: Context) {

    @Provides
    @Singleton
    open fun providesApiManager() = ApiManager(context)

    @Provides
    @Singleton
    open fun providesMovieApi(apiManager: ApiManager) = apiManager.getApi()

}