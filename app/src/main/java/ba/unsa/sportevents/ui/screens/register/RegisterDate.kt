package ba.unsa.sportevents.ui.screens.register


import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.ui.navigation.Screen
import com.google.gson.Gson
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.*

private fun makeToast(context: Context, message: String){

    val handler = Handler(Looper.getMainLooper())
    handler.post {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterFormDate(navController: NavController ,user: String?) {

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

        // Fetching the Local Context
        val mContext = LocalContext.current

        // Initializing a Calendar
        val mCalendar = Calendar.getInstance()

        // Declaring a string value to store date in string format
        val mDate = remember { mutableStateOf("") }

        // Declaring DatePickerDialog and setting initial values as current values (present year, month and day)
        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mCalendar.set(mYear, mMonth, mDayOfMonth)
                mDate.value = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(mCalendar.time)
            }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)
        )

        Column(
            modifier = Modifier
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(350.dp)
                    .height(45.dp)
                    .border(BorderStroke(1.dp, color = Color.Black))
                    .clickable(onClick = {
                        mDatePickerDialog.show()
                    })
            ) {
                Text(
                    text = mDate.value.ifEmpty { "yyyy-MM-dd" },
                    modifier = Modifier.padding(10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                var decodedUser = URLDecoder.decode(user, "UTF-8")

                val gson = Gson()
                val u = gson.fromJson(decodedUser, User::class.java)
                u.dateOfBirth = mDate.value

                    if(u.dateOfBirth!="") {
                       navController.navigate("${Screen.RegisterUsernameScreen.route}/${gson.toJson(u)}")
                }
                else {
                    makeToast(mContext,"Date Of Birth Input Required")
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


