package com.rapidsos.database.di_modules

import android.content.Context
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.database.database.MLDatabase
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