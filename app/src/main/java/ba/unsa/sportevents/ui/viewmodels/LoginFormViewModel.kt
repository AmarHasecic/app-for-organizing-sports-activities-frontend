package ba.unsa.sportevents.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import ba.unsa.sportevents.data.network.ActivitiesApiService
import ba.unsa.sportevents.data.repository.UserRepository

class LoginFormViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    suspend fun performLogin(username: String, password: String, navController: NavController) {
        userRepository.performLogin(username, password, navController)
    }
}