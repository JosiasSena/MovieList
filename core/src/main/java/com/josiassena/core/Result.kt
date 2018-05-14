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
@Entity
data class Result(@PrimaryKey
                  @SerializedName("id")
                  @Expose
                  var id: Int? = null,

                  @SerializedName("adult")
                  @Expose
                  var adult: Boolean? = null,

                  @ColumnInfo(name = "backdrop_path")
                  @SerializedName("backdrop_path")
                  @Expose
                  var backdropPath: String? = null,

                  @SerializedName("genre_ids")
                  @Expose
                  var genreIds: List<@JvmSuppressWildcards Int>? = null,

                  @ColumnInfo(name = "original_language")
                  @SerializedName("original_language")
                  @Expose
                  var originalLanguage: String? = null,

                  @ColumnInfo(name = "original_title")
                  @SerializedName("original_title")
                  @Expose
                  var originalTitle: String? = null,

                  @SerializedName("overview")
                  @Expose
                  var overview: String? = null,

                  @ColumnInfo(name = "release_date")
                  @SerializedName("release_date")
                  @Expose
                  var releaseDate: String? = null,

                  @ColumnInfo(name = "poster_path")
                  @SerializedName("poster_path")
                  @Expose
                  var posterPath: String? = null,

                  @SerializedName("popularity")
                  @Expose
                  var popularity: Double? = null,

                  @SerializedName("title")
                  @Expose
                  var title: String? = null,

                  @SerializedName("video")
                  @Expose
                  var video: Boolean? = null,

                  @ColumnInfo(name = "vote_average")
                  @SerializedName("vote_average")
                  @Expose
                  var voteAverage: Double? = null,

                  @ColumnInfo(name = "vote_count")
                  @SerializedName("vote_count")
                  @Expose
                  var voteCount: Int? = null) : Parcelable