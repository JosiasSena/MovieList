package com.josiassena.movielist.app_helpers.dependency_injection.modules

import com.josiassena.movieapi.Api
import com.josiassena.movielist.app_helpers.data_providers.genre.GenreProvider
import com.josiassena.movielist.app_helpers.data_providers.movies.MovieProvider
import com.josiassena.movielist.app_helpers.data_providers.movies.TopRatedMoviesProvider
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

    @Provides
    @Singleton
    open fun providesTopRatedMovieProvider(api: Api, databaseManager: DatabaseManager):
            TopRatedMoviesProvider {
        return TopRatedMoviesProvider(api, databaseManager)
    }

}