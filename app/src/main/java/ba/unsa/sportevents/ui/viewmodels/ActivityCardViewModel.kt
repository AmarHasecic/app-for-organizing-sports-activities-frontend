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

class ActivityCardViewModel(
    private val activityRepository: ActivityRepository,

) : ViewModel() {

    private val _activity = MutableStateFlow<SportActivity?>(null)
    val activity: StateFlow<SportActivity?> = _activity



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