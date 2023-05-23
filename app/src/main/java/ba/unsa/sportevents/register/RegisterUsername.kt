package ba.unsa.sportevents.register


import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ba.unsa.sportevents.RetrofitInstance
import ba.unsa.sportevents.login.performLogin
import ba.unsa.sportevents.model.User
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.net.URLDecoder

suspend fun performRegister(user: User, navController: NavController) {

    val registerResponse = coroutineScope {
        RetrofitInstance.userApi.registerUser(user)
    }

    if(registerResponse.isSuccessful) {

            performLogin(user.username, user.password, navController)
    }
}

private fun makeToast(context: Context, message: String){

    val handler = Handler(Looper.getMainLooper())
    handler.post {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterUsername(navController: NavController,user: String?) {

    var username by remember { mutableStateOf("") }
    val mContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp) ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Text(
            text = "Username",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier

        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                var decodedUser = URLDecoder.decode(user, "UTF-8")

                val gson = Gson()
                val u = gson.fromJson(decodedUser, User::class.java)
                u.username = username

                GlobalScope.launch {

                        performRegister(u, navController)

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
