package com.josiassena.core

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author Josias Sena
 */
@Parcelize
@Entity(tableName = "genre_movie_results")
class GenreMovieResults(@PrimaryKey
                        @SerializedName("id")
                        @Expose
                        var id: Int = 0,

                        @SerializedName("page")
                        @Expose
                        var page: Int? = null,

                        @SerializedName("results")
                        @Expose
                        var results: List<@JvmSuppressWildcards Result> = arrayListOf(),

                        @SerializedName("total_pages")
                        @ColumnInfo(name = "total_pages")
                        @Expose
                        var totalPages: Int? = null,

                        @SerializedName("total_results")
                        @ColumnInfo(name = "total_results")
                        @Expose
                        var totalResults: Int? = null) : Parcelable