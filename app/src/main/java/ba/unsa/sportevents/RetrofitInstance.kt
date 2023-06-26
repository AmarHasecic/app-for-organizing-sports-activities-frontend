package ba.unsa.sportevents

import ba.unsa.sportevents.api.ActivitiesApi
import ba.unsa.sportevents.api.UserApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val userApi: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.137.64:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    val activitiesApi: ActivitiesApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.137.64:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ActivitiesApi::class.java)
    }
}

