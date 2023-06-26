package ba.unsa.sportevents.main_page.tabs


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ba.unsa.sportevents.RetrofitInstance
import ba.unsa.sportevents.model.SportActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


@Composable
fun MapScreen(token: String) {
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }


    val context: Context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    var longLat by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(longLat, 10f)
    }

    val activitiesList = remember { mutableStateListOf<SportActivity>() }


    LaunchedEffect(Unit) {
        // dodati provjeru je li dozvoljena lokacija

        @SuppressLint("MissingPermission")
        val location = fusedLocationClient.lastLocation.await()

        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude

            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.activitiesApi.getActivitiesNearby(latitude, longitude)
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

            longLat = LatLng(latitude, longitude)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(longLat, 10f)
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
        Switch(
            checked = uiSettings.zoomControlsEnabled,
            onCheckedChange = {
                uiSettings = uiSettings.copy(zoomControlsEnabled = it)
            }
        )
    }
}


