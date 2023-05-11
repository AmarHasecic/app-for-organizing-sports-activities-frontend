package ba.unsa.sportevents.register

import SportEventsTheme
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ba.unsa.sportevents.model.User
import ba.unsa.sportevents.navigation.Screen
import com.google.gson.Gson
import kotlinx.serialization.json.Json

private fun makeToast(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterFormPass(navController: NavController, user: String?) {

    var fullName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val mContext = LocalContext.current



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp) ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Text(
            text = "Name and password",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier

        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full name") },
            modifier = Modifier
                .fillMaxWidth()
        )

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

        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                val gson = Gson()
                val u = gson.fromJson(user, User::class.java)
                u.fullName = fullName
                u.password = password
                if(fullName!="" && password!="")
                    navController.navigate("${Screen.RegisterDateScreen.route}/${gson.toJson(u)}")
                else {
                    makeToast(mContext,"Name And Password Input Required")
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .height(50.dp),

            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFFF2500),
                contentColor = Color.White
            )
        ) {
            Text(text = "Next")
        }

    }
}

/*
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SportEventsTheme {
        RegisterFormPass()
    }
}

*/




