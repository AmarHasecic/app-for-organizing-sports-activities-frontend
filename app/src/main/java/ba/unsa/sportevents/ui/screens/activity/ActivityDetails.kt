package ba.unsa.sportevents.ui.screens.activity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.ui.viewmodels.ActivityDetailsViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.compose.*
import kotlinx.coroutines.*
import java.net.URLDecoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivityDetails(
    sportActivity: String,
    token: String,
    viewModel: ActivityDetailsViewModel
) {

    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    var latLng by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 15f)
    }
    var activity by remember {mutableStateOf<SportActivity?>(null)}


    var user = viewModel.user.collectAsState()
    var text by remember{ mutableStateOf("Join") }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {

        viewModel.getUser(token)

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

            text = if(user.value?.let { checkIfUserJoined(activity!!, it) } == true){
                "Leave event"
            } else "Join"
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFFFF2500)
            ) {
                Text(text = "Activity details",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                    backgroundColor = Color.White)
            {

                Button(
                    onClick = {

                        if(!checkIfUserJoined(activity!!, user.value!!)){
                            scope.launch {
                              join(activity!!, user.value!!,viewModel)
                            }
                            text = "Leave event"
                        }
                        else{
                            scope.launch {
                                leaveEvent(activity!!, user.value!!,viewModel)
                                text = "Join"
                            }

                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),

                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFFF2500),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = text
                    )
                }
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

fun checkIfUserJoined(a: SportActivity, u: User, ) : Boolean{

    for (item in a.participants) {
        if (item != null) {
            if(item.id == u.id) return true
        }

    }
    return false;
}

fun join(a: SportActivity, u: User, viewModel: ActivityDetailsViewModel){

    val sportActivity = SportActivity(
        id = a.id,
        host = a.host,
        title = a.title,
        sport = a.sport,
        description = a.description,
        location = a.location,
        startTime = a.startTime,
        date = a.date,
        numberOfParticipants = a.numberOfParticipants + 1,
        maxNumberOfParticipants = a.maxNumberOfParticipants,
        participants = a.participants + u
    )

    val user = User(
        id = u.id,
        email = u.email,
        fullName = u.fullName,
        password = "",
        dateOfBirth = u.dateOfBirth,
        username = u.username,
        sports = u.sports,
        activities = u.activities + sportActivity
    )


    viewModel.updateActivity(sportActivity)
    viewModel.updateUser(user)
}

fun leaveEvent(a: SportActivity, u: User, viewModel: ActivityDetailsViewModel){

    val sportActivity = SportActivity(
        id = a.id,
        host = u,
        title = a.title,
        sport = a.sport,
        description = a.description,
        location = a.location,
        startTime = a.startTime,
        date = a.date,
        numberOfParticipants = a.numberOfParticipants -1,
        maxNumberOfParticipants = a.maxNumberOfParticipants,
        participants = a.participants
    )

    val user = User(
        id = u.id,
        email = u.email,
        fullName = u.fullName,
        password = u.password,
        dateOfBirth = u.dateOfBirth,
        username = u.username,
        sports = u.sports,
        activities = u.activities
    )

    //ovo ne radi... ovo brisanje
    sportActivity.participants.toMutableList().remove(u)
    user.activities.toMutableList().remove(a)

    viewModel.updateActivity(sportActivity)
    viewModel.updateUser(user)
}

