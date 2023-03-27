package ba.unsa.sportevents.login

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LoginApi {

    @GET("/users/login/{username}/{password}")
    fun loginUser(
        @Path("username") username: String,
        @Path("password") password: String
    ) : Response<User>

 }