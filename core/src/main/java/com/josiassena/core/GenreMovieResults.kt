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
@Entity(tableName = "genre_movie_results")
class GenreMovieResults(@PrimaryKey @SerializedName("id") @Expose var id: Int = 0,
                             @SerializedName("page") @Expose var page: Int = 0,
                             @SerializedName("results") @Expose var results: List<@JvmSuppressWildcards Result> = arrayListOf(),
                             @SerializedName("total_pages") @ColumnInfo(name = "total_pages") @Expose var totalPages: Int = 0,
                             @SerializedName("total_results") @ColumnInfo(name = "total_results") @Expose var totalResults: Int = 0) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            arrayListOf(),
            parcel.readInt(),
            parcel.readInt()) {

        parcel.readList(results, Result::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(page)
        parcel.writeList(results)
        parcel.writeInt(totalPages)
        parcel.writeInt(totalResults)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<GenreMovieResults> {
        override fun createFromParcel(parcel: Parcel) = GenreMovieResults(parcel)
        override fun newArray(size: Int): Array<GenreMovieResults?> = newArray(size)
    }

    override fun toString(): String {
        return "GenreMovieResults(id=$id, " +
                "page=$page, " +
                "results=$results, " +
                "totalPages=$totalPages, " +
                "totalResults=$totalResults)"
    }
}