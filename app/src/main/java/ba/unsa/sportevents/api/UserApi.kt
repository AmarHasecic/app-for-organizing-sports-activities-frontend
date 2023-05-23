package ba.unsa.sportevents.api

import ba.unsa.sportevents.login.JwtDTO
import ba.unsa.sportevents.login.LoginDTO
import ba.unsa.sportevents.model.User
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @POST("/api/login")
    suspend fun loginUser(@Body loginDTO: LoginDTO): Response<JwtDTO>

    @POST("/api/user")
    suspend fun registerUser(@Body user: User): Response<Any>

    @GET("/api/user")
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): Response<User>


}