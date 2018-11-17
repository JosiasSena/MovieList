package com.josiassena.googleplacesapi.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Viewport(

        @SerializedName("northeast")
        @Expose
        var northeast: Northeast? = null,

        @SerializedName("southwest")
        @Expose
        var southwest: Southwest? = null

)