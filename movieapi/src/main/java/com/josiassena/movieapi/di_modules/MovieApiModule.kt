package com.josiassena.movieapi.di_modules

import android.content.Context
import com.josiassena.movieapi.ApiBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
@Module
open class MovieApiModule(private val context: Context) {

    @Provides
    @Singleton
    open fun providesApiManager() = ApiBuilder(context)

    @Provides
    @Singleton
    open fun providesMovieApi(apiBuilder: ApiBuilder) = apiBuilder.buildApi(ApiBuilder.DEFAULT_HOST)

}