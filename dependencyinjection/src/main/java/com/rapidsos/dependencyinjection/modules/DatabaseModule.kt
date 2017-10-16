package com.rapidsos.dependencyinjection.modules

import android.arch.persistence.room.Room
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
    open fun providesDatabaseManager() = DatabaseManager(context)

    @Provides
    @Singleton
    open fun providesBeaconDatabase(): MLDatabase =
            Room.databaseBuilder(context, MLDatabase::class.java, "movie_list_db")
                    .fallbackToDestructiveMigration()
                    .build()

}