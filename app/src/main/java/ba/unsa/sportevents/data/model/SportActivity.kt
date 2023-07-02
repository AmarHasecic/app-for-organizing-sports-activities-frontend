package ba.unsa.sportevents.data.model

import com.google.gson.annotations.SerializedName

data class SportActivity(
    @SerializedName("id") var id: String,
    @SerializedName("user") var user: User,
    @SerializedName("title") var title: String,
    @SerializedName("sport") var sport: String,
    @SerializedName("description") var description: String,
    @SerializedName("location") var location: Location,
    @SerializedName("startTime") var startTime: String,
    @SerializedName("endTime") var endTime: String,
    @SerializedName("numberOfParticipants")  var numberOfParticipants: Int,
    @SerializedName("maxNumberOfParticipants")  var maxNumberOfParticipants: Int,
    @SerializedName("participants")  var participants: List<User>
)