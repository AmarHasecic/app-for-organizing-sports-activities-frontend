package ba.unsa.sportevents.ui.screens.activity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ba.unsa.etf.R
import ba.unsa.sportevents.data.model.Location
import ba.unsa.sportevents.data.model.Sport
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.ui.components.formatToTime
import ba.unsa.sportevents.ui.navigation.Screen
import coil.compose.rememberImagePainter
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTime(dateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return dateTime.format(formatter)
}
@RequiresApi(Build.VERSION_CODES.O)
fun parseStringToLocalDateTime(dateTimeString: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    return LocalDateTime.parse(dateTimeString, formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeDisplay(dateTime: LocalDateTime, dateTimeFormat: String) {
    val formattedTime = dateTime.format(DateTimeFormatter.ofPattern(dateTimeFormat))

        Text(
            text = formattedTime,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 3.dp, end = 5.dp)
        )
}

val sports: List<Sport> = listOf(
    Sport("Basketball", R.drawable.card_basketball),
    Sport("Football", R.drawable.card_soccer),
    Sport("Volleyball", R.drawable.card_volleyball),
    Sport("Cycling", R.drawable.card_cycling),
    Sport("Badminton", R.drawable.card_badminton),
    Sport("Baseball", R.drawable.card_baseball),
    Sport("Golf", R.drawable.card_golf),
    Sport("Tennis", R.drawable.card_tennis),
    Sport("Yoga", R.drawable.joga)
)



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivityCard(

    navController: NavController,
    sportActivity: SportActivity,
    token: String

    ){

Box(
   modifier = Modifier.fillMaxWidth()
       .background(color = Color.White)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = Color.White
    ) {
        sports.find { sport -> sport.name == sportActivity.sport }
            ?.let { painterResource(it.iconDrawable) }?.let {
                Image(
                painter = it,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .height(105.dp)
                        .alpha(0.7f)
            )
            }
        Column(Modifier.padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = "https://ui-avatars.com/api/?name=${sportActivity.host?.fullName}&background=random&color=fff",
                        builder = {
                            error(R.drawable.profile_picture)
                            fallback(R.drawable.profile_picture)
                        }
                    ),
                    contentDescription = "Profile photo",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                sportActivity.host?.let {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(
                            text = it.fullName,
                            modifier = Modifier.padding(start = 8.dp),
                            color = Color.Black
                        )
                    }
                }
            }
            Text(
                text = sportActivity.title,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 15.dp, start = 12.dp, end = 12.dp),
                color = Color.Black,
                fontSize = 21.sp,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                modifier = Modifier.padding(top = 15.dp)
            ){

                Row(
                    modifier = Modifier.padding(3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ios_calendar),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)

                    )
                    Text(
                        text = formatDateTime(parseStringToLocalDateTime(
                           sportActivity.date )),
                        modifier = Modifier
                            .padding(horizontal = 5.dp),
                        color = Color.Black
                    )
                }

                Row(
                    modifier = Modifier.padding(3.dp),
                    verticalAlignment = Alignment.CenterVertically

                ){
                    Image(
                        painter = painterResource(id = R.drawable.ios_clock),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)

                    )

                    TimeDisplay(parseStringToLocalDateTime(
                        sportActivity.startTime), dateTimeFormat = "hh:mm" )

                }
            }
            Row(
                modifier = Modifier.padding(top = 10.dp, bottom = 15.dp)
            ){

                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.Black
                )
                Text(
                    text = sportActivity.location.name,
                    modifier = Modifier
                        .padding(horizontal = 10.dp),
                    color = Color.Black
                )

            }
            Text(
                text = sportActivity.description,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
                color = Color.DarkGray
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, bottom = 8.dp, end = 0.dp),
                contentAlignment = Alignment.BottomEnd
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                val gson = Gson()
                                navController.navigate("${Screen.ActivityDetails.route}/${gson.toJson(sportActivity)}/${token}")
                            } ),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.next),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(6.dp)
                    )
                }
            }
        }
    }
  }
}


/*
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewCreateActivityScreen() {
    ActivityCard()
}
*/


