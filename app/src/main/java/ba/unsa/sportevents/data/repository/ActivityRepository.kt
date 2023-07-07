package ba.unsa.sportevents.data.repository


import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.data.network.ActivitiesApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ActivityRepository(private val apiService: ActivitiesApiService)  {

    suspend fun getActivitiesNearby(latitude: Double, longitude: Double): List<SportActivity> {

        val response = apiService.getActivitiesNearby(latitude, longitude)

        return if (response.isSuccessful) {

            response.body() ?: emptyList()
        } else {

            emptyList()
        }
    }

}