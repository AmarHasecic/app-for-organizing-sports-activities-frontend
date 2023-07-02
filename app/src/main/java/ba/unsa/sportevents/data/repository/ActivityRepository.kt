package ba.unsa.sportevents.data.repository


import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.data.network.ActivitiesApiService

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