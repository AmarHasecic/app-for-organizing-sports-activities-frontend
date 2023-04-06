package ba.unsa.sportevents.model

data class Activity(
    val description: String,
    val endTime: String,
    val host: String,
    val id: String,
    val location: Location,
    val maxNumberOfParticipants: Int,
    val numberOfParticipants: Int,
    val participants: List<String>,
    val sport: Sport,
    val startTime: String,
    val title: String
)