package ba.unsa.sportevents.data.network

import ba.unsa.sportevents.data.model.SportActivity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivitiesApiService {

    @GET("/activities/nearby")
    suspend fun getActivitiesNearby(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<List<SportActivity>>

}