package ba.unsa.sportevents.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ba.unsa.sportevents.RetrofitInstance
import ba.unsa.sportevents.Screen
import kotlinx.coroutines.*


suspend fun performLogin(username: String, password: String, navController: NavController) {
    val response = coroutineScope {
        val loginDTO = LoginDTO(username, password)
        RetrofitInstance.loginApi.loginUser(loginDTO)
    }


    if (response.isSuccessful && response.body() != null) {

        withContext(Dispatchers.Main) {
            //navigate to a user main page
            navController.navigate("${Screen.UserMainPage.route}/${"uspjesno logovan"}")
        }


    } else {
        Log.e(TAG, "Response not successful")
    }
}
@Composable
fun LoginScreen(navController: NavController) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                GlobalScope.launch {
                    performLogin(username, password, navController)
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Forgot password?", fontSize = 13.sp)


    }
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SportEventsTheme {
        LoginPage()
    }
}
*/


