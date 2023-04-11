package ba.unsa.sportevents.login

import com.google.gson.annotations.SerializedName

class JwtDTO (
    @SerializedName("jwt") val jwt: String
)