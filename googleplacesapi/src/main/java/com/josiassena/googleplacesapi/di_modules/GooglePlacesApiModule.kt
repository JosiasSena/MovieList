package com.josiassena.googleplacesapi.di_modules

import com.josiassena.googleplacesapi.NearbyMovieTheatersProvider
import com.josiassena.googleplacesapi.network.ApiBuilder
import com.josiassena.googleplacesapi.network.GooglePlacesApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
@Module
open class GooglePlacesApiModule {

    @Provides
    @Singleton
    open fun providesGooglePlaceApiBuilder() = ApiBuilder()

    @Provides
    @Singleton
    open fun providesGooglePlacesApi(apiBuilder: ApiBuilder) = apiBuilder.buildApi()


    @Provides
    @Singleton
    open fun providesNearbyMovieTheatersProvider(api: GooglePlacesApi) = NearbyMovieTheatersProvider(api)

}