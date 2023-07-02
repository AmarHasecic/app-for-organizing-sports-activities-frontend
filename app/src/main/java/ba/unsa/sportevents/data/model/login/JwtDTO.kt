package ba.unsa.sportevents.data.model.login

import com.google.gson.annotations.SerializedName

class JwtDTO (
    @SerializedName("jwt") val jwt: String
)