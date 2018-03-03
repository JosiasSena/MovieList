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
@Entity(tableName = "genres")
class Genres(@PrimaryKey
             @SerializedName("genres")
             @Expose
             var genres: List<@JvmSuppressWildcards Genre> = arrayListOf()) : Parcelable