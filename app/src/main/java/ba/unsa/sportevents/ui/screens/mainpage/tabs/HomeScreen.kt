package ba.unsa.sportevents.ui.screens.mainpage.tabs

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import ba.unsa.sportevents.data.repository.DataRepository
import ba.unsa.sportevents.ui.components.SearchBar
import ba.unsa.sportevents.ui.navigation.Screen
import ba.unsa.sportevents.ui.screens.activity.ActivityCard
import ba.unsa.sportevents.ui.theme.MyFavGreen
import ba.unsa.sportevents.ui.viewmodels.MainPageViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    return gpsEnabled || networkEnabled
}


@RequiresApi(Build.VERSION_CODES.O)
fun parseStringToLocalDate(dateTimeString: String): LocalDate? {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        LocalDate.parse(dateTimeString, formatter)
    } catch (e: DateTimeParseException) {
        null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun parseStringToLocalTime(dateTimeString: String): LocalTime? {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)
        dateTime.toLocalTime()
    } catch (e: DateTimeParseException) {
        null
    }
}


@RequiresApi(Build.VERSION_CODES.O)
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
    ){}

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
                Manifest.permission.ACCESS_FINE_LOCATION
            )

        }
        else {
        }

            @SuppressLint("MissingPermission")
            val location = fusedLocationClient.lastLocation.await()

            if (location != null) {
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

                if(isLocationEnabled(context) && activities.value.isNotEmpty()) {
                    ShowLazyList(navController, activities.value.filter { activity ->
                        (activity.title.contains(
                            searchQuery.value,
                            ignoreCase = true
                        ) || activity.description.contains(
                            searchQuery.value,
                            ignoreCase = true
                        )
                                )
                                &&
                        (
                          activity?.let { parseStringToLocalDate(activity.date) }?.isAfter(LocalDate.now())== true
                        )
                    }, token)
                }
                if(activities.value.isEmpty()){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center

                    ){
                        Text(
                            text = "No activities to display",
                            color = Color.Black
                        )
                    }
                }
            }

        FloatingActionButton(
            onClick = {
                navController.navigate("${Screen.CreateActivity.route}/${token}")
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            backgroundColor = MyFavGreen
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowLazyList(navController: NavController,activities: List<SportActivity>, token: String) {
    LazyColumn {
        items(activities) { activity ->
            ActivityCard(navController,activity, token)
        }
    }
}

