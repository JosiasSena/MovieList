package com.josiassena.core

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Josias Sena
 */
@Entity (tableName = "movie_videos")
class MovieVideos(@PrimaryKey @SerializedName("id") @Expose var id: String = "",
                       @SerializedName("results") @Expose
                       var results: List<@JvmSuppressWildcards MovieVideosResult> = arrayListOf()) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            arrayListOf()) {

        parcel.readList(results, MovieVideosResult::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<MovieVideos> {
        override fun createFromParcel(parcel: Parcel) = MovieVideos(parcel)
        override fun newArray(size: Int): Array<MovieVideos?> = newArray(size)
    }

    override fun toString() = "MovieVideos(id='$id', results=$results)"

}