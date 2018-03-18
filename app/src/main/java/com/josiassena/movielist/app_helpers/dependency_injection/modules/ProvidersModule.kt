package com.josiassena.movielist.app_helpers.dependency_injection.modules

import com.josiassena.movieapi.Api
import com.josiassena.movielist.app_helpers.data_providers.GenreProvider
import com.josiassena.movielist.app_helpers.data_providers.MovieProvider
import com.rapidsos.database.database.DatabaseManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
@Module
open class ProvidersModule {

    @Provides
    @Singleton
    open fun providesGenreProvider(api: Api, databaseManager: DatabaseManager): GenreProvider {
        return GenreProvider(api, databaseManager)
    }

    @Provides
    @Singleton
    open fun providesMovieProvider(api: Api, databaseManager: DatabaseManager): MovieProvider {
        return MovieProvider(api, databaseManager)
    }

}