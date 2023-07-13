package ba.unsa.sportevents.ui.screens.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ba.unsa.etf.R
import ba.unsa.sportevents.data.model.Sport
import ba.unsa.sportevents.ui.components.SearchBar


@Composable
fun SportsScreen() {

    val sports : List<Sport> = listOf(
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

    val searchQuery = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFFFF2500)
            ) {
                Text(
                    text = "Pick a sport",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {

            Column(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
            ) {
                SearchBar(searchQuery.value) { newQuery ->
                    searchQuery.value = newQuery
                }

                Spacer(modifier = Modifier.height(20.dp))

                ShowListOfSports(sports = sports.filter { sport ->
                    sport.name.contains(
                        searchQuery.value,
                        ignoreCase = true
                    )
                })
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowListOfSports(sports: List<Sport>) {
    LazyColumn {
        items(sports) { sport ->
            Card(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row(modifier = Modifier.padding(4.dp)) {

                    Image(
                        painter = painterResource(sport.iconDrawable),
                        contentDescription = "Sport icon",
                        modifier = Modifier.size(70.dp)
                    )

                    Text(
                        text = sport.name,
                        modifier = Modifier.align(alignment = CenterVertically)
                            .padding(start = 15.dp)
                        )

                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewShowListOfSports() {
    SportsScreen()
}