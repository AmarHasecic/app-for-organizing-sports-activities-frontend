package ba.unsa.sportevents.ui.screens.mainpage.tabs


import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ba.unsa.sportevents.data.repository.DataRepository
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.ui.screens.activity.ActivityCard
import ba.unsa.sportevents.ui.viewmodels.MainPageViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MapScreen() {
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }


    val context: Context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    var latLng by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 10f)
    }

    val activitiesList = remember { mutableStateListOf<SportActivity>() }


    LaunchedEffect(Unit) {

        @SuppressLint("MissingPermission")
        val location = fusedLocationClient.lastLocation.await()

        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude

            try {
                val response = withContext(Dispatchers.IO) {
                    DataRepository.activitiesApiService.getActivitiesNearby(latitude, longitude)
                }

                if (response.isSuccessful) {
                    val activitiesNearby = response.body()
                    if (activitiesNearby != null) {
                        activitiesList.addAll(activitiesNearby)

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            latLng = LatLng(latitude, longitude)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 10f)
        }
    }



    Box(Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = properties,
            uiSettings = uiSettings,
            cameraPositionState = cameraPositionState
        ) {
            if (activitiesList.size != 0) {
                activitiesList.forEach() { activity ->

                    val date = activity?.let { parseStringToLocalDate(activity.date) }
                    val startTime = activity?.let { parseStringToLocalTime(activity.startTime) }
                    if(date?.isAfter(LocalDate.now()) == true && startTime?.isAfter(LocalTime.now()) == true) {
                        Marker(
                            state = MarkerState(
                                position = LatLng(
                                    activity.location.latitude,
                                    activity.location.longitude
                                )
                            ),
                            title = activity.title
                        )
                    }


                }
            }
        }
        Switch(
            checked = uiSettings.zoomControlsEnabled,
            onCheckedChange = {
                uiSettings = uiSettings.copy(zoomControlsEnabled = it)
            }
        )


    }

}


