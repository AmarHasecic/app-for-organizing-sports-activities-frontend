package ba.unsa.sportevents.ui.screens.register

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.ui.components.CustomButton
import ba.unsa.sportevents.ui.navigation.Screen
import ba.unsa.sportevents.ui.theme.MyFavGreen
import com.google.gson.Gson
import java.net.URLDecoder



private fun makeToast(context: Context, message: String){

    val handler = Handler(Looper.getMainLooper())
    handler.post {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterFormPass(navController: NavController, user: String?) {

    var fullName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

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

        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                IconButton(
                    onClick = { passwordVisibility = !passwordVisibility },
                    modifier = Modifier.size(30.dp).align(Alignment.CenterVertically).padding(end = 5.dp)
                ) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password visibility"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(text = "Next") {
            var decodedUser = URLDecoder.decode(user, "UTF-8")

            val gson = Gson()
            val u = gson.fromJson(decodedUser, User::class.java)
            u.fullName = fullName
            u.password = password
            if(fullName!="" && password!="")
            {
                navController.navigate("${Screen.RegisterDateScreen.route}/${gson.toJson(u)}")
            }
            else {
                makeToast(mContext,"Name And Password Input Required")
            }
        }
    }
}




