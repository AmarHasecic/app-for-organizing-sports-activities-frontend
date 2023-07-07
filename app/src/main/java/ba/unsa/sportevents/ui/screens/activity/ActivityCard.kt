package ba.unsa.sportevents.ui.screens.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ba.unsa.etf.R
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.ui.navigation.Screen
import com.google.gson.Gson


@Composable
fun ActivityCard(navController: NavController, sportActivity: SportActivity) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Color(0x1BE0E0E0)
    ) {
        Column(Modifier.padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.profile_picture),
                    contentDescription = "Profile photo",
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = sportActivity.host.fullName,
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color.Black
                )
            }
            Text(
                text = sportActivity.title,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(top = 8.dp,start = 12.dp),
                color = Color.Black
            )
            Text(
                text = sportActivity.description,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 8.dp,start = 12.dp),
                color = Color.Black
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp, end = 8.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Button(
                    onClick = {
                        val gson = Gson()
                        navController.navigate("${Screen.ActivityDetails.route}/${gson.toJson(sportActivity)}")
                    },
                    modifier = Modifier,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFFF2500),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Details")
                }
            }
        }
    }
}

