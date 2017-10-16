package com.josiassena.core

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Josias Sena
 */
@Entity (tableName = "movie_videos_result")
class MovieVideosResult(@PrimaryKey @SerializedName("id") @Expose var id: String = "",
                             @ColumnInfo(name = "iso_639_1")
                             @SerializedName("iso_639_1") @Expose var iso6391: String = "",
                             @ColumnInfo(name = "iso_3166_1")
                             @SerializedName("iso_3166_1") @Expose var iso31661: String = "",
                             @SerializedName("key") @Expose var key: String = "",
                             @SerializedName("name") @Expose var name: String = "",
                             @SerializedName("site") @Expose var site: String = "",
                             @SerializedName("size") @Expose var size: Int = 0,
                             @SerializedName("type") @Expose var type: String = "") : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(iso6391)
        parcel.writeString(iso31661)
        parcel.writeString(key)
        parcel.writeString(name)
        parcel.writeString(site)
        parcel.writeInt(size)
        parcel.writeString(type)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<MovieVideosResult> {
        override fun createFromParcel(parcel: Parcel) = MovieVideosResult(parcel)

        override fun newArray(size: Int): Array<MovieVideosResult?> = newArray(size)
    }

    override fun toString(): String {
        return "MovieVideosResult(id='$id', " +
                "iso6391='$iso6391', " +
                "iso31661='$iso31661', " +
                "key='$key', name='$name', " +
                "site='$site', " +
                "size=$size, " +
                "type='$type')"
    }
}