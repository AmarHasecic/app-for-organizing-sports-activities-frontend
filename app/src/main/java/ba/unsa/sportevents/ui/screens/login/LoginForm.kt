package ba.unsa.sportevents.ui.screens.login

import android.content.Context
import android.os.Handler
import android.os.Looper
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ba.unsa.sportevents.ui.viewmodels.LoginFormViewModel
import kotlinx.coroutines.*


private fun makeToast(context: Context, message: String){

    val handler = Handler(Looper.getMainLooper())
    handler.post {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginFormViewModel) {

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
                        viewModel.performLogin(username, password, navController)
                    }
                    catch (e:Exception){
                        e.message?.let { makeToast(mContext, it) }
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

