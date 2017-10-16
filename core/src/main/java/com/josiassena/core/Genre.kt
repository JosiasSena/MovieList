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
class Genre(@PrimaryKey @SerializedName("id") @Expose var id: Int = 0,
                 @SerializedName("name") @Expose var name: String = "") : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    companion object CREATOR : Parcelable.Creator<Genre> {
        override fun createFromParcel(parcel: Parcel) = Genre(parcel)
        override fun newArray(size: Int): Array<Genre?> = newArray(size)
    }

    override fun describeContents() = 0

    override fun toString() = "Genre(id=$id, name='$name')"
}