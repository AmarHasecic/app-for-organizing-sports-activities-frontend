package ba.unsa.sportevents.data.model

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("id") var id: String,
    @SerializedName("email") var email: String,
    @SerializedName("fullName") var fullName: String,
    @SerializedName("password") var password: String,
    @SerializedName("dateOfBirth") var dateOfBirth: String,
    @SerializedName("username") var username: String,
    @SerializedName("sports") var sports: List<String>,
    @SerializedName("activities")  var activities: List<SportActivity>,
)