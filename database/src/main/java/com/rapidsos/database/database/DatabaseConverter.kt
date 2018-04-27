package com.rapidsos.database.database

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.josiassena.core.Genre
import com.josiassena.core.MovieResults
import com.josiassena.core.MovieVideosResult
import com.josiassena.core.Result

/**
 * @author Josias Sena
 */
class DatabaseConverter {

    @TypeConverter
    fun fromIntList(list: List<@JvmSuppressWildcards Int>): String = Gson().toJson(list)

    @TypeConverter
    fun toIntList(json: String): List<Int> = Gson().fromJson(json, object : TypeToken<List<Int>>() {}.type)

    @TypeConverter
    fun fromMovieVideosResultList(list: List<@JvmSuppressWildcards MovieVideosResult>): String = Gson().toJson(list)

    @TypeConverter
    fun toMovieVideosResultList(json: String): List<@JvmSuppressWildcards MovieVideosResult> = Gson().fromJson(json, object : TypeToken<List<MovieVideosResult>>() {}.type)

    @TypeConverter
    fun fromResultList(list: List<@JvmSuppressWildcards Result>): String = Gson().toJson(list)

    @TypeConverter
    fun toResultList(json: String): List<@JvmSuppressWildcards Result> = Gson().fromJson(json, object : TypeToken<List<Result>>() {}.type)

    @TypeConverter
    fun fromGenreList(list: List<@JvmSuppressWildcards Genre>): String = Gson().toJson(list)

    @TypeConverter
    fun toGenreList(json: String): List<@JvmSuppressWildcards Genre> = Gson().fromJson(json, object : TypeToken<List<Genre>>() {}.type)

    @TypeConverter
    fun fromGenreMovieResultsList(list: List<@JvmSuppressWildcards MovieResults>): String = Gson().toJson(list)

    @TypeConverter
    fun toGenreMovieResultsList(json: String): List<@JvmSuppressWildcards MovieResults> = Gson().fromJson(json, object : TypeToken<List<MovieResults>>() {}.type)

}