package ba.unsa.sportevents.login

data class User(
    val activities: List<Any>,
    val dateOfBirth: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val sports: List<String>,
    val username: String
)