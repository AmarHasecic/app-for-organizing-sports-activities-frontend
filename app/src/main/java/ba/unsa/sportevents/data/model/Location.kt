package ba.unsa.sportevents.data.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("name") val name: String
)