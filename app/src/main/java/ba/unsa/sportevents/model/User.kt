package ba.unsa.sportevents.model

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("email") val email: String,
    @SerializedName("fullName") val fullName: String,
    @SerializedName("password") val password: String,
    @SerializedName("dateOfBirth") val dateOfBirth: String,
    @SerializedName("username") val username: String,
    @SerializedName("sports") val sports: List<Sport>,
    @SerializedName("activities")  val activities: List<Activity>,
)