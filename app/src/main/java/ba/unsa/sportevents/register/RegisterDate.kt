package ba.unsa.sportevents.register


import SportEventsTheme
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ba.unsa.etf.R
import ba.unsa.sportevents.composables.DatePicker
import ba.unsa.sportevents.model.User
import com.google.gson.Gson


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterFormDate(navController: NavController ,user: String?) {

    var date by remember {
        mutableStateOf("")
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {


        Image(
            painter = painterResource(id = R.drawable.birthdaycake),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Add your date of birth",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier

        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "This won't be part of your public profile.",
            fontSize = 15.sp,
            modifier = Modifier

        )

        Spacer(modifier = Modifier.height(15.dp))

        DatePicker();

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                val gson = Gson()
                val u = gson.fromJson(user, User::class.java)
                u.dateOfBirth = date

                println(date)


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


