package ba.unsa.sportevents

import ba.unsa.sportevents.login.LoginApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstnce {

    val loginApi: LoginApi by lazy {
        Retrofit.Builder()
            .baseUrl("localhost:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }
}

