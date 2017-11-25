package com.josiassena.movielist.app_helpers.dependency_injection.modules

import com.josiassena.movielist.app_helpers.data_providers.GenreProvider
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.helpers.api.Api
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
    open fun providesGenreProvider(api: Api, databaseManager: DatabaseManager) =
            GenreProvider(api, databaseManager)

}