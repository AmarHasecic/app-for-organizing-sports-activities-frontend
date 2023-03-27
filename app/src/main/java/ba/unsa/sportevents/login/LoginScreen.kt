package ba.unsa.sportevents.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ba.unsa.sportevents.Screen
import ba.unsa.sportevents.login.ui.theme.SportEventsTheme

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
                 /*
                     val response = RetrofitInstnce.loginApi.loginUser(username, password)
                     if(response.isSuccessful && response.body()!=null){

                         //navigate to a user main page

                     }
                     else{
                         Log.e(TAG, "Response not successful")
                     }

                  */

                //hardcoded verzija za testiranje navigacije
                val user = User(
                    activities = listOf("hiking", "biking", "swimming"),
                    dateOfBirth = "1990-05-10",
                    email = "johndoe@example.com",
                    firstName = "John",
                    lastName = "Doe",
                    password = "p@ssw0rd",
                    sports = listOf("basketball", "football", "tennis"),
                    username = "johndoe123"
                )

                navController.navigate("${Screen.UserMainPage.route}/${user.username}")



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


