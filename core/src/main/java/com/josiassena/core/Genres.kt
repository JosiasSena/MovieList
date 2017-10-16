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
@Entity
class Genres(@PrimaryKey @SerializedName("genres") @Expose
             var genres: List<@JvmSuppressWildcards Genre> = arrayListOf()) : Parcelable {

    constructor(parcel: Parcel) : this(arrayListOf()) {
        parcel.readList(genres, Genre::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(genres)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Genres> {
        override fun createFromParcel(parcel: Parcel) = Genres(parcel)
        override fun newArray(size: Int): Array<Genres?> = newArray(size)
    }

    override fun toString() = "Genres(genres=$genres)"
}