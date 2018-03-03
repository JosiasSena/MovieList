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
@Entity
class Genre(@PrimaryKey
            @SerializedName("id")
            @Expose
            var id: Int = 0,

            @SerializedName("name")
            @Expose
            var name: String? = null) : Parcelable