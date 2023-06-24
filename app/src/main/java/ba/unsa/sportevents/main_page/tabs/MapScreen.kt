package ba.unsa.sportevents.main_page.tabs


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


@Composable
fun MapScreen(token: String){
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }



    val context: Context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    var longLat by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(longLat, 10f)
    }


    LaunchedEffect(Unit) {

        //dodati provjeru je li dozvoljena lokacija
            @SuppressLint("MissingPermission")
            val location = fusedLocationClient.lastLocation
            location.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val latitude = task.result.latitude
                    val longitude = task.result.longitude
                    longLat = LatLng(latitude, longitude)
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(longLat, 10f)
                }
            }


            //fetch all activities around me

    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = properties,
            uiSettings = uiSettings,
            cameraPositionState = cameraPositionState
        )
        Switch(
            checked = uiSettings.zoomControlsEnabled,
            onCheckedChange = {
                uiSettings = uiSettings.copy(zoomControlsEnabled = it)
            }
        )
    }
}
