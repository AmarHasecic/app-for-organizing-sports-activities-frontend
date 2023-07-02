package ba.unsa.sportevents.data.repository

import androidx.navigation.NavController
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.data.model.login.LoginDTO
import ba.unsa.sportevents.data.network.UserApiService
import ba.unsa.sportevents.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository(private val apiService: UserApiService) {

    suspend fun getUser(token: String): Response<User> {
        return withContext(Dispatchers.IO) {
            apiService.getUser("Bearer $token")
        }
    }

    suspend fun performLogin(username: String, password: String, navController: NavController) {
        val tokenResponse = coroutineScope {
            val loginDTO = LoginDTO(username, password)
            DataRepository.userApiService.loginUser(loginDTO)
        }

        if (tokenResponse.isSuccessful && tokenResponse.body() != null) {

            val token = tokenResponse.body()!!.jwt

            withContext(Dispatchers.Main) {

                navController.navigate("${Screen.UserMainPage.route}/${token}")
            }

        } else {
            throw Exception(tokenResponse.message())
        }
    }

    suspend fun performRegister(user: User, navController: NavController) {

        val registerResponse = coroutineScope {
            DataRepository.userApiService.registerUser(user)
        }

        if(registerResponse.isSuccessful) {

            performLogin(user.username, user.password, navController)
        }
    }
}
