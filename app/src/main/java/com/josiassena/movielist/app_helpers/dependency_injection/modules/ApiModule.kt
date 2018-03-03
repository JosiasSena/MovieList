package com.josiassena.movielist.app_helpers.dependency_injection.modules

import android.content.Context
import com.rapidsos.helpers.api.ApiBuilder
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
    open fun providesApiManager() = ApiBuilder(context)

    @Provides
    @Singleton
    open fun providesMovieApi(apiBuilder: ApiBuilder) = apiBuilder.buildApi(ApiBuilder.DEFAULT_HOST)

}