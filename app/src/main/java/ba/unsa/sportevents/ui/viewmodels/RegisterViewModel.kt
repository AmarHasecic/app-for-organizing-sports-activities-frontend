package ba.unsa.sportevents.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.data.repository.UserRepository

class RegisterViewModel (
    private val userRepository: UserRepository,
) : ViewModel() {

    suspend fun performRegister(user: User, navController: NavController) {
        userRepository.performRegister(user, navController)
    }
}