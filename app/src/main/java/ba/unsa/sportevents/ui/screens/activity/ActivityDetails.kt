package ba.unsa.sportevents.ui.screens.activity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ba.unsa.etf.R
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.ui.components.CustomButton
import ba.unsa.sportevents.ui.navigation.Screen
import ba.unsa.sportevents.ui.theme.MyFavGreen
import ba.unsa.sportevents.ui.viewmodels.ActivityDetailsViewModel
import coil.compose.rememberImagePainter
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
    val user = viewModel.user.collectAsState()
    var text by remember{ mutableStateOf("Join") }

    val scope = rememberCoroutineScope()
    var isInitialDataFetched by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.user) {

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
        }

    }
    SideEffect {
        if (activity != null && user.value != null && !isInitialDataFetched) {
            isInitialDataFetched = true
            text = if (checkIfUserJoined(activity!!, user.value!!)) {
                "Leave event"
            } else {
                "Join"
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MyFavGreen
            ) {
                Text(text = "Activity details",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold), color = Color.White,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                    backgroundColor = Color.White)
            {

                CustomButton(text = text) {
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
                LazyColumn(Modifier.padding(8.dp)) {

                    item{
                    activity?.let {
                        Text(
                            text = it.description,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 20.dp)
                                .height(IntrinsicSize.Min),
                            color = Color.DarkGray
                        )
                    }
                    }
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Divider(
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                                    .height(1.dp)
                                    .fillMaxWidth(0.95f),
                                color = Color.LightGray
                            )
                        }
                    }
                    item{
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
                                    activity?.date ?: ""
                                )),
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

                            activity?.let {
                                parseStringToLocalDateTime(
                                    it.startTime)
                            }?.let { TimeDisplay(it, dateTimeFormat = "hh:mm" ) }

                        }
                    }
                    }

                    item {
                        Row(
                            modifier = Modifier.padding(top = 10.dp, bottom = 15.dp)
                        ) {

                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color.Black
                            )
                            activity?.location?.let {
                                Text(
                                    text = it.name,
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp),
                                    color = Color.Black
                                )
                            }

                        }
                    }

                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Divider(
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                                    .height(1.dp)
                                    .fillMaxWidth(0.95f),
                                color = Color.White
                            )
                        }
                    }

                    item{
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center

                        ) {
                            Text(
                                text = "Spots left: " + ((activity?.maxNumberOfParticipants ?: 0) - (activity?.numberOfParticipants
                                ?: 0)),
                                color = Color.Black
                            )
                        }
                    }

                    item{
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center

                        ) {
                            LinearProgressIndicator(progress = ((activity?.numberOfParticipants ?: 0) / (activity?.maxNumberOfParticipants
                                ?: 1).toFloat()))
                            println((activity?.numberOfParticipants ?: 0) / (activity?.maxNumberOfParticipants
                                ?: 1))
                        }
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

fun checkIfUserJoined(activity: SportActivity, user: User, ) : Boolean{
    val userFound = activity.participants.find { it!!.id == user.id }
    if(userFound!=null) return true
    return false
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

fun leaveEvent(a: SportActivity, u: User, viewModel: ActivityDetailsViewModel) {



    val sportActivity = SportActivity(
        id = a.id,
        host = a.host,
        title = a.title,
        sport = a.sport,
        description = a.description,
        location = a.location,
        startTime = a.startTime,
        date = a.date,
        numberOfParticipants = a.numberOfParticipants - 1,
        maxNumberOfParticipants = a.maxNumberOfParticipants,
        participants = a.participants
    )

    val user = User(
        id = u.id,
        email = u.email,
        fullName = u.fullName,
        password = "",
        dateOfBirth = u.dateOfBirth,
        username = u.username,
        sports = u.sports,
        activities = u.activities
    )

    removeUserFromParticipants(sportActivity, user.id)
    removeActivityFromUser(user,sportActivity.id)

    viewModel.updateActivity(sportActivity)
    viewModel.updateUser(user)
}

fun removeUserFromParticipants(activity: SportActivity, userId: String) {
    val updatedParticipants = activity.participants.toMutableList()
    val userToRemove = updatedParticipants.find { it!!.id == userId }
    if (userToRemove != null) {
        updatedParticipants.remove(userToRemove)
        activity.participants = updatedParticipants
    }
}

fun removeActivityFromUser(user: User, activityId: String) {
    val updatedActivities = user.activities.toMutableList()
    val activityToRemove = updatedActivities.find { it!!.id == activityId }
    if (activityToRemove != null) {
        updatedActivities.remove(activityToRemove)
        user.activities = updatedActivities
    }
}


