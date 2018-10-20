package com.josiassena.movielist

import android.content.Context
import com.josiassena.database.database.DatabaseManager
import com.josiassena.database.database.MLDatabase
import com.josiassena.database.di_modules.DatabaseModule
import org.mockito.Mockito

/**
 * @author Josias Sena
 */
class DatabaseManagerTestModule(context: Context) : DatabaseModule(context) {

    override fun providesBeaconDatabase(): MLDatabase = Mockito.mock(MLDatabase::class.java)

    override fun providesDatabaseManager(database: MLDatabase): DatabaseManager =
            Mockito.mock(DatabaseManager::class.java)
}