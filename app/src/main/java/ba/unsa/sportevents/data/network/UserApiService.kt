package ba.unsa.sportevents.data.network

import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.data.model.login.JwtDTO
import ba.unsa.sportevents.data.model.login.LoginDTO
import ba.unsa.sportevents.data.model.User
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {

    @POST("/api/login")
    suspend fun loginUser(@Body loginDTO: LoginDTO): Response<JwtDTO>

    @POST("/api/user")
    suspend fun registerUser(@Body user: User): Response<Any>

    @GET("/api/user")
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): Response<User>

    @PUT("/api/user/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body user: User
    ): Response<User>

}