package com.josiassena.googleplacesapi.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OpeningHours(

        @SerializedName("open_now")
        @Expose
        var openNow: Boolean? = null

)