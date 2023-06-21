package ba.unsa.sportevents.reusable

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.provider.Settings
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

@Composable
fun LocationPopUp(requestPermission: Int){

    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    var location by remember { mutableStateOf<Location?>(null) }
    var permissionRequested by remember { mutableStateOf(false) }

    LaunchedEffect(permissionRequested) {
        if (!permissionRequested) {
            permissionRequested = true
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission already granted, request location updates
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { loc: Location? ->
                        location = loc
                    }
            } else {
                // Request location permission
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    requestPermission
                )
            }
        }
    }

    // Use the location data
    if (location != null) {
        // Do something with the location coordinates
        val latitude = location!!.latitude
        val longitude = location!!.longitude
    }

}