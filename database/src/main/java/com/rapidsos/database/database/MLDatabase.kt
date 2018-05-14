package com.rapidsos.database.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.josiassena.core.*
import com.rapidsos.database.daos.*

/**
 * @author Josias Sena
 */
@Database(version = 2, entities = [Genre::class, MovieResults::class, Genres::class,
    MovieVideos::class, MovieVideosResult::class, Result::class], exportSchema = false)
@TypeConverters(DatabaseConverter::class)
abstract class MLDatabase : RoomDatabase() {

    abstract fun genreDao(): GenreDao

    abstract fun genresDao(): GenresDao

    abstract fun genreMovieResultsDao(): GenreMovieResultsDao

    abstract fun movieVideosDao(): MovieVideosDao

    abstract fun movieVideosResultDao(): MovieVideosResultDao

    abstract fun resultDao(): ResultDao

    companion object {
        fun getDatabase(context: Context): MLDatabase {
            return Room.databaseBuilder(context, MLDatabase::class.java, "movie_list_db")
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }

}