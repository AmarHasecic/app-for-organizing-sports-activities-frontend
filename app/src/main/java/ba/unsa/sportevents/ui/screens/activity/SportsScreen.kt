package ba.unsa.sportevents.ui.screens.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ba.unsa.etf.R
import ba.unsa.sportevents.data.model.Sport
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.ui.components.CustomButton
import ba.unsa.sportevents.ui.components.SearchBar
import ba.unsa.sportevents.ui.navigation.Screen
import ba.unsa.sportevents.ui.theme.MyFavGreen
import com.google.gson.Gson


@Composable
fun SportsScreen(

    token: String,
    navController: NavController,
    activity: String

){
    val sports: List<Sport> = listOf(
    Sport("Basketball", R.drawable.basketball),
    Sport("Football", R.drawable.football),
    Sport("Volleyball", R.drawable.volleyball),
    Sport("Cycling", R.drawable.cycling),
    Sport("Badminton", R.drawable.badminton),
    Sport("Baseball", R.drawable.baseball),
    Sport("Golf", R.drawable.golf),
    Sport("Tennis", R.drawable.tennis),
    Sport("Yoga", R.drawable.joga)
)

var searchQuery by remember { mutableStateOf("") }
var selectedSport by remember { mutableStateOf<Sport?>(null) }
var sportActivity by remember {
        mutableStateOf<SportActivity?>(null)
    }

    LaunchedEffect(Unit) {
        val gson = Gson()
        sportActivity = gson.fromJson(activity, SportActivity::class.java)
    }


Scaffold(
topBar = {
    TopAppBar(
        backgroundColor = MyFavGreen
    ) {
        Text(
            text = "Pick a sport",
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold), color = Color.White,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
},

bottomBar = {
    CustomButton(text = "Next") {
        if(selectedSport!!.isSelected) {

            sportActivity!!.sport = selectedSport!!.name

            val gson = Gson()
            val jsonActivity : String = gson.toJson(sportActivity)
            navController.navigate("${Screen.SearchPlaceScreen.route}/${token}/${jsonActivity}")

        }

    }
}

) { contentPadding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        SearchBar(searchQuery) { newQuery ->
            searchQuery = newQuery
        }

        Spacer(modifier = Modifier.height(20.dp))

        ShowListOfSports(sports = sports.filter { sport ->
            sport.name.contains(
                searchQuery,
                ignoreCase = true
            )
        }) { sport ->
            sports.forEach { it.isSelected = it == sport }
            selectedSport = sport
        }
    }
}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowListOfSports(sports: List<Sport>, onSportSelected: (Sport) -> Unit) {

    var selectedSportIndex by remember { mutableStateOf(-1) }

    LazyColumn {
        itemsIndexed(sports) { index, sport ->
            Card(
                onClick = {
                    onSportSelected(sport)
                    selectedSportIndex = index
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                backgroundColor = if (index == selectedSportIndex) Color(0xFADFFDCC) else Color.White,
            ) {
                Row(modifier = Modifier.padding(4.dp)) {
                    Image(
                        painter = painterResource(sport.iconDrawable),
                        contentDescription = "Sport icon",
                        modifier = Modifier.size(70.dp)
                    )

                    Text(
                        text = sport.name,
                        modifier = Modifier
                            .align(alignment = CenterVertically)
                            .padding(start = 15.dp),
                        color = Color.Black,
                    )
                }
            }
        }
    }
}
