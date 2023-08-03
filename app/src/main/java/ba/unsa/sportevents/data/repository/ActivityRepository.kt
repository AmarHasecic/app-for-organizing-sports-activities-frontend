package ba.unsa.sportevents.data.repository


import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.data.network.ActivitiesApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
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

    suspend fun createActivity(sportActivity: SportActivity) {

        val response = coroutineScope {
            DataRepository.activitiesApiService.createActivity(sportActivity)
        }

        if(!response.isSuccessful) {
            //handle error
        }

    }
    suspend fun updateActivity(sportActivity: SportActivity) : Response<SportActivity> {

        return withContext(Dispatchers.IO) {
            apiService.updateActivity(sportActivity.id, sportActivity)
        }
    }

    suspend fun getActivitiesByHostId(hostId:  String): List<SportActivity> {

        val response = apiService.getActivitiesByHostId(hostId)

        return if (response.isSuccessful) {

            response.body() ?: emptyList()
        } else {

            emptyList()
        }
    }
    suspend fun deleteActivity(sportActivity: SportActivity) : Response<Any> {

        return withContext(Dispatchers.IO) {
            apiService.deleteActivity(sportActivity.id)
        }
    }


}