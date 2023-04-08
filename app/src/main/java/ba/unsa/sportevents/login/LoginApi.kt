package ba.unsa.sportevents.login

import ba.unsa.sportevents.model.User
import retrofit2.Response
import retrofit2.http.*

interface LoginApi {

    @POST("/api/login")
    suspend fun loginUser(@Body loginDTO: LoginDTO): Response<User>


}