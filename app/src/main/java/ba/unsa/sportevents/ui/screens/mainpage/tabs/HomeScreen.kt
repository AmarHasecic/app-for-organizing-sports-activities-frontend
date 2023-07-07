package ba.unsa.sportevents.ui.screens.mainpage.tabs

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.navigation.NavController
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.ui.components.SearchBar
import ba.unsa.sportevents.ui.navigation.Screen
import ba.unsa.sportevents.ui.screens.activity.ActivityCard
import ba.unsa.sportevents.ui.viewmodels.MainPageViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await

@Composable
fun HomeScreen(navController: NavController, token: String, viewModel: MainPageViewModel){

    val searchQuery = remember { mutableStateOf("") }

    val context: Context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val activities = viewModel.activities.collectAsState()


    LaunchedEffect(Unit) {

        @SuppressLint("MissingPermission")
        val location = fusedLocationClient.lastLocation.await()

        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude

            viewModel.getActivitiesNearby(latitude, longitude)

        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        if (activities.value.isNotEmpty()) {
            Column {
                SearchBar(searchQuery.value) { newQuery ->
                    searchQuery.value = newQuery
                }
                Spacer(modifier = Modifier.height(8.dp))

                    ShowLazyList(navController,activities.value.filter { activity ->
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
        else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No activities to display",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                )
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

