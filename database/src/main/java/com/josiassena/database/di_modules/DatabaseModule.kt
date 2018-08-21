package com.josiassena.database.di_modules

import android.content.Context
import com.josiassena.database.database.DatabaseManager
import com.josiassena.database.database.MLDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
@Module
open class DatabaseModule(val context: Context) {

    @Provides
    @Singleton
    open fun providesBeaconDatabase(): MLDatabase = MLDatabase.getDatabase(context)

    @Provides
    @Singleton
    open fun providesDatabaseManager(database: MLDatabase) = DatabaseManager(database)
}