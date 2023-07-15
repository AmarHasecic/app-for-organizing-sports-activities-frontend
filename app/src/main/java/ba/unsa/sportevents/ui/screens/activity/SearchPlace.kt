package ba.unsa.sportevents.ui.screens.activity

import android.location.Geocoder
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ba.unsa.sportevents.ui.navigation.Screen
import ba.unsa.sportevents.ui.viewmodels.MainPageViewModel
import java.util.*


@Composable
fun SearchPlaceScreen(

    token: String,
    viewModel: MainPageViewModel,
    navController: NavController


){
  //  val user = viewModel.user.collectAsState()


    var query by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf(emptyList<Place>()) }
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.getUser(token)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFFFF2500)
            ) {
                Text(
                    text = "Sport activity location",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        },

        bottomBar = {
            Button(
                onClick = {
                    navController.navigate("${Screen.UserMainPage.route}/${token}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFF2500),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Create activity")
            }
        }
    ) { contentPadding ->

        Box(modifier = Modifier.padding(contentPadding)) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
            .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search for a place") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray
                )
            )
            searchResults.forEach { place ->
                Text(text = place.name)
                // Coordinates: ${place.latitude}, ${place.longitude}
            }
        }
        }
    }

    LaunchedEffect(query) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val results = geocoder.getFromLocationName(query, 5)

        if (results != null) {
            searchResults = results.mapNotNull { result ->
                if (result.hasLatitude() && result.hasLongitude()) {
                    Place(result.featureName ?: "", result.latitude, result.longitude)
                } else {
                    null
                }
            }
        }
    }

}

data class Place(val name: String, val latitude: Double, val longitude: Double)

/*
@Preview
@Composable
fun PreviewSearchPlaceScreen() {
    SearchPlaceScreen()
}
 */