package ba.unsa.sportevents.google_signin


data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)