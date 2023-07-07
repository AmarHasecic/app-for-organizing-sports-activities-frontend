package ba.unsa.sportevents.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.data.repository.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActivityDetailsViewModel(
    private val activityRepository: ActivityRepository
) : ViewModel() {

    private val _activity = MutableStateFlow<SportActivity?>(null)
    val activity: StateFlow<SportActivity?> = _activity
}