package ba.unsa.sportevents.data.network

import ba.unsa.sportevents.data.model.SportActivity
import retrofit2.Response
import retrofit2.http.*

interface ActivitiesApiService {

    @GET("/activities/nearby")
    suspend fun getActivitiesNearby(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<List<SportActivity>>

    @GET("/activities/host/{hostId}")
    suspend fun getActivitiesByHostId(
        @Path("hostId") hostId: String,
    ): Response<List<SportActivity>>

    @POST("/activities")
    suspend fun createActivity(@Body sportActivity: SportActivity): Response<Any>

    @PUT("/activities/{id}")
    suspend fun updateActivity(
        @Path("id") id: String,
        @Body sportActivity: SportActivity
    ): Response<SportActivity>

}