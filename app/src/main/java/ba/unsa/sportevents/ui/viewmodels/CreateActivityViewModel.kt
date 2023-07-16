package ba.unsa.sportevents.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.data.repository.ActivityRepository
import ba.unsa.sportevents.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateActivityViewModel (
    private val activityRepository: ActivityRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _activities = MutableStateFlow<List<SportActivity>>(emptyList())
    val activities: StateFlow<List<SportActivity>> = _activities


    suspend fun createActivityRequest(sportActivity: SportActivity) {
        activityRepository.createActivity(sportActivity)
    }

    fun getUser(token: String) {
        viewModelScope.launch {
            val response = userRepository.getUser(token)
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    _user.value = user
                }
            } else {
                // Handle error
            }
        }
    }
}