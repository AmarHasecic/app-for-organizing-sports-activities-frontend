package ba.unsa.sportevents.ui.screens.activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ba.unsa.sportevents.data.model.Location
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.ui.components.CustomButton
import ba.unsa.sportevents.ui.components.makeToast
import ba.unsa.sportevents.ui.navigation.Screen
import ba.unsa.sportevents.ui.theme.MyFavGreen
import ba.unsa.sportevents.ui.viewmodels.CreateActivityViewModel
import ba.unsa.sportevents.ui.viewmodels.MainPageViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


data class AutocompleteResult(
    val address: String,
    val placeId: String
)

fun searchPlaces(query: String, placesClient: PlacesClient, locationAutofill: MutableList<AutocompleteResult>) {
    locationAutofill.clear()
    val request = FindAutocompletePredictionsRequest.builder().setQuery(query).build()
    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            locationAutofill += response.autocompletePredictions.map {
                AutocompleteResult(
                    it.getFullText(null).toString(),
                    it.placeId
                )
            }
        }
        .addOnFailureListener {
            it.printStackTrace()
            println(it.cause)
            println(it.message)
        }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SearchPlaceScreen(

    token: String,
    viewModel: CreateActivityViewModel,
    navController: NavController,
    activity: String


) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }
    val placesClient: PlacesClient = remember { Places.createClient(context) }
    val locationAutofill = remember { mutableStateListOf<AutocompleteResult>() }

    var selectedLocation by remember { mutableStateOf(AutocompleteResult("","")) }


    var sportActivity by remember {
        mutableStateOf<SportActivity?>(null)
    }

    val user = viewModel.user.collectAsState()


    LaunchedEffect(Unit) {

        viewModel.getUser(token)

        val gson = Gson()
        sportActivity = gson.fromJson(activity, SportActivity::class.java)

    }


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MyFavGreen
            ) {
                Text(
                    text = "Sport activity location",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold), color = Color.White,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        },

        bottomBar = {

            CustomButton(text = "Create activity") {
                sportActivity!!.host = user.value

                val placeFields = listOf(Place.Field.LAT_LNG)
                val request = FetchPlaceRequest.newInstance(selectedLocation.placeId, placeFields)
                placesClient.fetchPlace(request)
                    .addOnSuccessListener {
                        if (it != null) {

                            sportActivity!!.location = Location(
                                latitude = it.place.latLng.latitude,
                                longitude = it.place.latLng.longitude,
                                name = selectedLocation.address
                            )

                            // Post to a server
                            GlobalScope.launch {
                                viewModel.createActivityRequest(sportActivity!!)
                            }

                            navController.navigate("${Screen.UserMainPage.route}/${token}")
                            makeToast(context, "Activity created successfully")

                        }
                    }
                    .addOnFailureListener {
                        it.printStackTrace()
                    }
            }
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    searchPlaces(it, placesClient,locationAutofill )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textStyle = TextStyle(color = Color.Black)
            )

            AnimatedVisibility(
                locationAutofill.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(locationAutofill) { place ->
                        LocationSuggestionItem(place) {
                            text = it.address
                            locationAutofill.clear()
                            selectedLocation =  place
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }


        }
    }
}

@Composable
fun LocationSuggestionItem(

    place: AutocompleteResult,
    onItemClick: (AutocompleteResult) -> Unit


) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(place) }
            .padding(8.dp)
            .background(color = Color.White),
        shape = RoundedCornerShape(6.dp),
        elevation = 4.dp
    ) {
        Text(
          text = place.address,
            color = Color.Black,
            modifier = Modifier.background(color = Color.White)
        )
    }
}



