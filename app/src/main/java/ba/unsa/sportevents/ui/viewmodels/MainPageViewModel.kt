package ba.unsa.sportevents.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.data.network.ActivitiesApiService
import ba.unsa.sportevents.data.repository.ActivityRepository
import ba.unsa.sportevents.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainPageViewModel(
    private val userRepository: UserRepository,
    private val activitiesRepository: ActivityRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _activities = MutableStateFlow<List<SportActivity>>(emptyList())
    val activities: StateFlow<List<SportActivity>> = _activities

    private val _activitiesByHost = MutableStateFlow<List<SportActivity>>(emptyList())
    val activitiesByHost: StateFlow<List<SportActivity>> = _activitiesByHost


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

    fun getActivitiesNearby(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val activities = activitiesRepository.getActivitiesNearby(latitude, longitude)
            _activities.value = activities
        }
    }

    fun getActivitiesByHostId(hostId: String) {
        viewModelScope.launch {
            val activitiesByHost = activitiesRepository.getActivitiesByHostId(hostId)
            _activitiesByHost.value = activitiesByHost
        }
    }

}