package com.josiassena.movielist

import android.content.Context
import com.josiassena.movielist.app_helpers.dependency_injection.modules.DatabaseModule
import com.rapidsos.database.database.DatabaseManager
import org.mockito.Mockito

/**
 * @author Josias Sena
 */
class DatabaseManagerTestModule(context: Context) : DatabaseModule(context) {

    override fun providesDatabaseManager(): DatabaseManager =
            Mockito.mock(DatabaseManager::class.java)
}