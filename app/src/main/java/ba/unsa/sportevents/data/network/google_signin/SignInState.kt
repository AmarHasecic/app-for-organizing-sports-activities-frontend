package ba.unsa.sportevents.data.network.google_signin


data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)