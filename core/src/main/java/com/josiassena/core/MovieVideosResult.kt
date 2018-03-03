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
@Entity(tableName = "movie_videos_result")
data class MovieVideosResult(@PrimaryKey
                             @SerializedName("id")
                             @Expose
                             var id: String = "",

                             @ColumnInfo(name = "iso_639_1")
                             @SerializedName("iso_639_1")
                             @Expose
                             var iso6391: String? = null,

                             @ColumnInfo(name = "iso_3166_1")
                             @SerializedName("iso_3166_1")
                             @Expose
                             var iso31661: String? = null,

                             @SerializedName("key")
                             @Expose
                             var key: String? = null,

                             @SerializedName("name")
                             @Expose
                             var name: String? = null,

                             @SerializedName("site")
                             @Expose
                             var site: String? = null,

                             @SerializedName("size")
                             @Expose
                             var size: Int? = null,

                             @SerializedName("type")
                             @Expose
                             var type: String? = null) : Parcelable