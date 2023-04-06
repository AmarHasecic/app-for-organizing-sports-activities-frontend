package ba.unsa.sportevents.login

import ba.unsa.sportevents.model.User
import retrofit2.Response
import retrofit2.http.*

interface LoginApi {

        @GET("/users/login/{username}/{password}")
       suspend fun loginUser(
            @Path("username") username: String,
            @Path("password") password: String
        ) : Response<User>

     }