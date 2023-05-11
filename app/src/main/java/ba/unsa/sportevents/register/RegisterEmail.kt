package ba.unsa.sportevents.register

import android.content.Context
import android.os.Build
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
import ba.unsa.sportevents.model.User
import ba.unsa.sportevents.navigation.Screen
import com.google.gson.Gson


private fun makeToast(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterFormEmail(navController: NavController) {


    var email by remember {
        mutableStateOf("")
    }

    val user = User(
        email = "",
        fullName = "",
        password = "",
        dateOfBirth = "",
        username = "",
        sports = emptyList(),
        activities = emptyList()
    )

    val mContext = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Text(
            text = "Sign up",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier

        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                user.email = email
                val gson = Gson()

                if(email!="")
                     navController.navigate("${Screen.RegisterNamePassScreen.route}/${gson.toJson(user)}")
                else{
                        makeToast(mContext,"Email Input Required")
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


