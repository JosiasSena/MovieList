package com.josiassena.movielist.app_helpers.dependency_injection.modules

import com.josiassena.database.database.DatabaseManager
import com.josiassena.googleplacesapi.network.GooglePlacesApi
import com.josiassena.movieapi.Api
import com.josiassena.movielist.app_helpers.data_providers.genre.GenreProvider
import com.josiassena.movielist.app_helpers.data_providers.movies.MovieProvider
import com.josiassena.movielist.app_helpers.data_providers.movies.TopRatedMoviesProvider
import com.josiassena.movielist.app_helpers.data_providers.theaters_nearby.NearbyMovieTheatersProvider
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
    open fun providesGenreProvider(api: Api, databaseManager: DatabaseManager) = GenreProvider(api, databaseManager)

    @Provides
    @Singleton
    open fun providesMovieProvider(api: Api, databaseManager: DatabaseManager) = MovieProvider(api, databaseManager)

    @Provides
    @Singleton
    open fun providesTopRatedMovieProvider(api: Api, databaseManager: DatabaseManager) = TopRatedMoviesProvider(api, databaseManager)

    @Provides
    @Singleton
    open fun providesNearbyMovieTheatersProvider(api: GooglePlacesApi) = NearbyMovieTheatersProvider(api)

}