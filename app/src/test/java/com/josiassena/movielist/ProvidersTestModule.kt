package com.josiassena.movielist

import com.josiassena.movielist.app_helpers.data_providers.GenreProvider
import com.josiassena.movielist.app_helpers.dependency_injection.modules.ProvidersModule
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.helpers.api.Api
import org.mockito.Mockito

/**
 * @author Josias Sena
 */
class ProvidersTestModule : ProvidersModule() {

    override fun providesGenreProvider(api: Api, databaseManager: DatabaseManager): GenreProvider =
            Mockito.mock(GenreProvider::class.java)
}