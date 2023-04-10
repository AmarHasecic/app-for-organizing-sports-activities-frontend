package ba.unsa.sportevents.register


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ba.unsa.etf.R
import ba.unsa.sportevents.Composables.DatePicker


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterFormDate() {

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

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
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
        RegisterFormDate()
    }
}
*/





