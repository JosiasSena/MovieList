package com.josiassena.movielist

import android.content.Context
import com.josiassena.movielist.app_helpers.dependency_injection.modules.DatabaseModule
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.database.database.MLDatabase
import org.mockito.Mockito

/**
 * @author Josias Sena
 */
class DatabaseManagerTestModule(context: Context) : DatabaseModule(context) {

    override fun providesBeaconDatabase(): MLDatabase = Mockito.mock(MLDatabase::class.java)

    override fun providesDatabaseManager(database: MLDatabase): DatabaseManager =
            Mockito.mock(DatabaseManager::class.java)
}