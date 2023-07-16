package ba.unsa.sportevents.data.network

import ba.unsa.sportevents.data.model.SportActivity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ActivitiesApiService {

    @GET("/activities/nearby")
    suspend fun getActivitiesNearby(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<List<SportActivity>>

    @POST("/activities")
    suspend fun createActivity(@Body sportActivity: SportActivity): Response<Any>

}