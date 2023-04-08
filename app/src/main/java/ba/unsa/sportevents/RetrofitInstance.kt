package ba.unsa.sportevents

import ba.unsa.sportevents.login.LoginApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val loginApi: LoginApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.0.63:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }
}

