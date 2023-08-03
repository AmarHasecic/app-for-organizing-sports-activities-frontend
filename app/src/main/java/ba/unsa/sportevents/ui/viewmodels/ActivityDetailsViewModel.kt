package ba.unsa.sportevents.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.data.repository.ActivityRepository
import ba.unsa.sportevents.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActivityDetailsViewModel(
    private val activityRepository: ActivityRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _activity = MutableStateFlow<SportActivity?>(null)
    val activity: StateFlow<SportActivity?> = _activity

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

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

    fun updateActivity(sportActivity: SportActivity) {
        viewModelScope.launch {
            val response = activityRepository.updateActivity(sportActivity)
            if (response.isSuccessful) {
                val activity = response.body()
                if (activity != null) {
                    _activity.value = activity
                }
            } else {
                // Handle error
            }
        }
    }
    fun updateUser(user: User) {
        viewModelScope.launch {
            val response = userRepository.updateUser(user)
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

    fun deleteActivity(sportActivity: SportActivity) {
        viewModelScope.launch {
            val response = activityRepository.deleteActivity(sportActivity)
            if (response.isSuccessful) {
                throw Exception("Sport activity deleted")
            } else {
                throw Exception("Error")
            }
        }
    }

}