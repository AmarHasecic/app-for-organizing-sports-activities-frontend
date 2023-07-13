package ba.unsa.sportevents.data.repository

import ba.unsa.sportevents.data.network.ActivitiesApiService
import ba.unsa.sportevents.data.network.UserApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataRepository {
        private val retrofit = Retrofit.Builder()
            .baseUrl("http://10.10.1.41:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userApiService: UserApiService = retrofit.create(UserApiService::class.java)
        val activitiesApiService: ActivitiesApiService = retrofit.create(ActivitiesApiService::class.java)
}

