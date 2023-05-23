package ba.unsa.sportevents.login

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ba.unsa.sportevents.RetrofitInstance
import ba.unsa.sportevents.model.User
import ba.unsa.sportevents.navigation.Screen
import kotlinx.coroutines.*


suspend fun performLogin(username: String, password: String, navController: NavController) {
    val tokenResponse = coroutineScope {
        val loginDTO = LoginDTO(username, password)
        RetrofitInstance.userApi.loginUser(loginDTO)
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

private fun makeToast(context: Context, message: String){

    val handler = Handler(Looper.getMainLooper())
    handler.post {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}
@Composable
fun LoginScreen(navController: NavController) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val mContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login")
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
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                GlobalScope.launch {
                    try {
                        performLogin(username, password, navController)
                    }
                    catch (e:Exception){
                        e.message?.let { makeToast(mContext, it) };
                    }
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),

            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFFF2500),
                contentColor = Color.White
            )
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Forgot password?", fontSize = 13.sp)

    }
}

