package ba.unsa.sportevents.api

import ba.unsa.sportevents.model.SportActivity
import kotlinx.serialization.json.Json
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivitiesApi {

    @GET("/activities/nearby")
    suspend fun getActivitiesNearby(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<List<SportActivity>>

}