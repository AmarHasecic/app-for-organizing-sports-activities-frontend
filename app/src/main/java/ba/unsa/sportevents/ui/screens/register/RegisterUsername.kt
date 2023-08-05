package ba.unsa.sportevents.ui.screens.register


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
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.ui.components.CustomButton
import ba.unsa.sportevents.ui.theme.MyFavGreen
import ba.unsa.sportevents.ui.viewmodels.RegisterViewModel
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.net.URLDecoder



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterUsername(navController: NavController,user: String?,viewModel: RegisterViewModel) {

    var username by remember { mutableStateOf("") }
    val context = LocalContext.current

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

        CustomButton(text = "Finish") {

                var decodedUser = URLDecoder.decode(user, "UTF-8")

                val gson = Gson()
                val u = gson.fromJson(decodedUser, User::class.java)
                u.username = username

                GlobalScope.launch {

                    try {
                        viewModel.performRegister(u, navController)
                    }catch(e: Exception){
                        ba.unsa.sportevents.ui.components.makeToast(context, e.message.toString())
                    }
                }

        }
    }
}
