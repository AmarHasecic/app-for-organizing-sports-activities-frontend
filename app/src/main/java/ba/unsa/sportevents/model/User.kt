package ba.unsa.sportevents.model

data class User(
    val activities: List<Activity>,
    val dateOfBirth: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val sports: List<Sport>,
    val username: String
)