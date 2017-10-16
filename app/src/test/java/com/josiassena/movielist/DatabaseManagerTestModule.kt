package com.josiassena.movielist

import android.content.Context
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.dependencyinjection.modules.DatabaseModule
import org.mockito.Mockito

/**
 * @author Josias Sena
 */
class DatabaseManagerTestModule(context: Context) : DatabaseModule(context) {

    override fun providesDatabaseManager(): DatabaseManager =
            Mockito.mock(DatabaseManager::class.java)
}