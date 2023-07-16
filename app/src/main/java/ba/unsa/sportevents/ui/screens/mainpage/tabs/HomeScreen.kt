package ba.unsa.sportevents.ui.screens.mainpage.tabs

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.ui.components.SearchBar
import ba.unsa.sportevents.ui.navigation.Screen
import ba.unsa.sportevents.ui.screens.activity.ActivityCard
import ba.unsa.sportevents.ui.viewmodels.MainPageViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await

fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    return gpsEnabled || networkEnabled
}

@Composable
fun HomeScreen(navController: NavController, token: String, viewModel: MainPageViewModel){

    val searchQuery = remember { mutableStateOf("") }

    val context: Context = LocalContext.current
    val activities = viewModel.activities.collectAsState()

    val fusedLocationClient: FusedLocationProviderClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }

    // Request location permission
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, retrieve the user's last known location
        } else {
            // Permission denied, handle accordingly (e.g., show an error message)
        }
    }



    LaunchedEffect(Unit) {

         if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
             // Permission not granted, request it
             requestPermissionLauncher.launch(
                 Manifest.permission.ACCESS_FINE_LOCATION)

        }
        else{
              val location = fusedLocationClient.lastLocation.await()

             val latitude = location.latitude
             val longitude = location.longitude

             viewModel.getActivitiesNearby(latitude, longitude)
         }
    }

    Box(modifier = Modifier.fillMaxSize()) {

            Column {
                SearchBar(searchQuery.value) { newQuery ->
                    searchQuery.value = newQuery
                }
                Spacer(modifier = Modifier.height(8.dp))

                if(isLocationEnabled(context)) {
                    ShowLazyList(navController, activities.value.filter { activity ->
                        activity.title.contains(
                            searchQuery.value,
                            ignoreCase = true
                        ) || activity.description.contains(
                            searchQuery.value,
                            ignoreCase = true
                        )
                    })
                }
            }

        FloatingActionButton(
            onClick = {
                navController.navigate("${Screen.CreateActivity.route}/${token}")
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            backgroundColor = Color(0xFFFF2500)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}

@Composable
fun ShowLazyList(navController: NavController,activities: List<SportActivity>) {
    LazyColumn {
        items(activities) { activity ->
            ActivityCard(navController,activity)
        }
    }
}

