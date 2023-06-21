package ba.unsa.sportevents.main_page.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ba.unsa.sportevents.main_page.ActivityCard
import ba.unsa.sportevents.model.Activity
import ba.unsa.sportevents.reusable.SearchBar
import ba.unsa.sportevents.sealed.DataState

@Composable
fun HomeScreen(token: String){

    val searchQuery = remember { mutableStateOf("") }
    val dataState = remember { mutableStateOf<DataState>(DataState.Loading) }


    LaunchedEffect(Unit) {
       //dohvatit sve aktivnbosti iz baze koje su u blizini usera
    }
    val activityList
            : List<Activity> = listOf() //za potrebe testiranja


        when (val state = dataState.value) {
            is DataState.Success -> {
                val activityList
                        : List<Activity> = listOf()
                Column {
                    SearchBar(searchQuery.value) { newQuery ->
                        searchQuery.value = newQuery
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ShowLazyList(activityList.filter { activity ->
                        activity.title?.contains(searchQuery.value, ignoreCase = true) == true
                                ||  activity.description?.contains(searchQuery.value, ignoreCase = true) == true
                    })
                }
            }
            is DataState.Failure -> {
                val errorMessage = state.message
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = errorMessage,
                        fontSize = MaterialTheme.typography.h5.fontSize,
                    )
                }
            }
            DataState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            DataState.Empty -> {
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
        }
    Box(modifier = Modifier.fillMaxSize()) {
        // Circular plus floating button
        FloatingActionButton(
            onClick = {
                //navController.navigate("CreateActivityScreen")
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
fun ShowLazyList(activities: List<Activity>) {
    LazyColumn {
        items(activities) { activity ->
            ActivityCard(activity)
        }
    }
}

