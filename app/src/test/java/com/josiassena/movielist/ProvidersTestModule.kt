package com.josiassena.movielist

import com.josiassena.database.database.DatabaseManager
import com.josiassena.movieapi.Api
import com.josiassena.movielist.app_helpers.data_providers.genre.GenreProvider
import com.josiassena.movielist.app_helpers.dependency_injection.modules.ProvidersModule
import org.mockito.Mockito

/**
 * @author Josias Sena
 */
class ProvidersTestModule : ProvidersModule() {

    override fun providesGenreProvider(api: Api, databaseManager: DatabaseManager): GenreProvider =
            Mockito.mock(GenreProvider::class.java)
}