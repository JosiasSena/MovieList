package com.josiassena.core

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
@Entity(tableName = "movie_videos")
class MovieVideos(@PrimaryKey
                  @SerializedName("id")
                  @Expose
                  var id: String = "",

                  @SerializedName("results")
                  @Expose
                  var results: List<@JvmSuppressWildcards MovieVideosResult>? = null) : Parcelable