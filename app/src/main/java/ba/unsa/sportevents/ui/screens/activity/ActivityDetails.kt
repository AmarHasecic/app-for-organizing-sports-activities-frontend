package ba.unsa.sportevents.ui.screens.activity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ba.unsa.sportevents.data.model.SportActivity
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLDecoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivityDetails(sportActivity: String) {

    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    var latLng by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 15f)
    }
    var activity by remember {mutableStateOf<SportActivity?>(null)}




    LaunchedEffect(Unit) {

        val decodedActivity = withContext(Dispatchers.IO) {
            URLDecoder.decode(sportActivity, "UTF-8")
        }
        val gson = Gson()
        activity = gson.fromJson(decodedActivity, SportActivity::class.java)


        if(activity!=null) {
            val latitude = activity!!.location.latitude
            val longitude = activity!!.location.longitude


            latLng = LatLng(latitude, longitude)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFFFF2500)
            ) {
                Text(text = "Activity details")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
            ) {
                GoogleMap(
                    modifier = Modifier.matchParentSize(),
                    properties = properties,
                    uiSettings = uiSettings,
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = MarkerState(
                            position = latLng
                        ),
                        title = activity?.title
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(
                        text = "${activity?.description}",
                        color = Color.DarkGray,
                    )

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Divider(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .height(1.dp)
                                .fillMaxWidth(0.9f),
                            color = Color.LightGray
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Start: ${activity?.let { getTime(it.startTime) }}",
                            color = Color.DarkGray,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "End: ${activity?.let { getTime(it.endTime) }}",
                            color = Color.DarkGray,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Participants: ${activity?.numberOfParticipants}",
                            color = Color.DarkGray,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Max. participants: ${activity?.maxNumberOfParticipants}",
                            color = Color.DarkGray,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Button(
                onClick = {
                        //Join action
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),

                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFF2500),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Join")
            }


        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun extractHoursAndMinutes(dateTimeString: String): Pair<Int, Int> {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val localDateTime = LocalDateTime.parse(dateTimeString, formatter)

    val hour = localDateTime.hour
    val minute = localDateTime.minute

    return hour to minute
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun getTime(dateTimeString: String) : String{
    val hoursAndMinutes = extractHoursAndMinutes(dateTimeString)

    val timeText = run {
        val (hour, minute) = hoursAndMinutes
        "$hour:$minute"
    }
    return timeText
}

