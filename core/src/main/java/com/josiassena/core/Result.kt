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
@Entity
class Result(@SerializedName("adult") @Expose var adult: Boolean = false,
             @ColumnInfo(name = "backdrop_path") @SerializedName("backdrop_path") @Expose var backdropPath: String? = "",
             @SerializedName("genre_ids") @Expose var genreIds: List<@JvmSuppressWildcards Int> = arrayListOf(),
             @PrimaryKey @SerializedName("id") @Expose var id: Int = 0,
             @ColumnInfo(name = "original_language") @SerializedName("original_language") @Expose var originalLanguage: String = "",
             @ColumnInfo(name = "original_title") @SerializedName("original_title") @Expose var originalTitle: String = "",
             @SerializedName("overview") @Expose var overview: String = "",
             @ColumnInfo(name = "release_date") @SerializedName("release_date") @Expose var releaseDate: String? = "",
             @ColumnInfo(name = "poster_path") @SerializedName("poster_path") @Expose var posterPath: String = "",
             @SerializedName("popularity") @Expose var popularity: Double = 0.0,
             @SerializedName("title") @Expose var title: String? = "",
             @SerializedName("video") @Expose var video: Boolean = false,
             @ColumnInfo(name = "vote_average") @SerializedName("vote_average") @Expose var voteAverage: Double = 0.0,
             @ColumnInfo(name = "vote_count") @SerializedName("vote_count") @Expose var voteCount: Int = 0) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            arrayListOf(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readDouble(),
            parcel.readInt()) {

        parcel.readList(genreIds, Int::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.writeByte(if (adult) 1 else 0)
        parcel?.writeString(backdropPath)
        parcel?.writeList(genreIds)
        parcel?.writeInt(id)
        parcel?.writeString(originalLanguage)
        parcel?.writeString(originalTitle)
        parcel?.writeString(overview)
        parcel?.writeString(releaseDate)
        parcel?.writeString(posterPath)
        parcel?.writeDouble(popularity)
        parcel?.writeString(title)
        parcel?.writeByte(if (video) 1 else 0)
        parcel?.writeDouble(voteAverage)
        parcel?.writeInt(voteCount)
    }

    override fun describeContents() = 0

    override fun toString(): String {
        return "Result(adult=$adult, " +
                "backdropPath=$backdropPath, " +
                "genreIds=$genreIds, " +
                "id=$id, " +
                "originalLanguage=$originalLanguage, " +
                "originalTitle=$originalTitle, " +
                "overview=$overview, " +
                "releaseDate=$releaseDate," +
                "posterPath=$posterPath, " +
                "popularity=$popularity, " +
                "title=$title, " +
                "video=$video, " +
                "voteAverage=$voteAverage, " +
                "voteCount=$voteCount)"
    }

    companion object CREATOR : Parcelable.Creator<Result> {
        override fun createFromParcel(parcel: Parcel) = Result(parcel)
        override fun newArray(size: Int): Array<Result?> = newArray(size)
    }

}