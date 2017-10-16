package com.rapidsos.database.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.josiassena.core.*
import com.rapidsos.database.daos.*

/**
 * @author Josias Sena
 */
@Database(version = 1, entities = arrayOf(Genre::class, GenreMovieResults::class, Genres::class,
        MovieVideos::class, MovieVideosResult::class, Result::class), exportSchema = false)
@TypeConverters(DatabaseConverter::class)
abstract class MLDatabase : RoomDatabase() {

    abstract fun genresDao(): GenresDao

    abstract fun genreMovieResultsDao(): GenreMovieResultsDao

    abstract fun movieVideosDao(): MovieVideosDao

    abstract fun movieVideosResultDao(): MovieVideosResultDao

    abstract fun resultDao(): ResultDao

}