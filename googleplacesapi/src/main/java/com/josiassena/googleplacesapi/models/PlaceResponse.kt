package com.josiassena.googleplacesapi.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlaceResponse(

        @SerializedName("error_message")
        @Expose
        var errorMessage: String? = null,

        @SerializedName("html_attributions")
        @Expose
        var htmlAttributions: List<String>? = null,

        @SerializedName("results")
        @Expose
        var places: List<Place>? = null,

        @SerializedName("status")
        @Expose
        var status: String? = null

)
