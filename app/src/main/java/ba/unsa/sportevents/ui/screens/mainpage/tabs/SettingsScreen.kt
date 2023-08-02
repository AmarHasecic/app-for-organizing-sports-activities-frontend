package ba.unsa.sportevents.ui.screens.mainpage.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ba.unsa.etf.R
import ba.unsa.sportevents.ui.navigation.Screen


@Composable
fun SettingsScreen(navController: NavController){


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center

    ){
    Column(
        modifier = Modifier.height(getScreenHeightInDp() - 200.dp),
        verticalArrangement =  Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Image(
            painter = painterResource(R.drawable.notimplemented),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),

        )
    }

    Spacer(modifier = Modifier.weight(1f))
    Button(
        onClick = {
            navController.navigate(Screen.LoginFrontPage.route) {
                popUpTo(Screen.LoginFrontPage.route) {
                    inclusive = true
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 20.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(6.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.logout),
                contentDescription = "Log out icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Log Out")
        }
    }
  }
}
