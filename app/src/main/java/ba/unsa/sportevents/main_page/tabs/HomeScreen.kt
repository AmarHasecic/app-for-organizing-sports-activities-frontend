package ba.unsa.sportevents.main_page.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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

@Composable
fun HomeScreen(token: String){

    val searchQuery = remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
       //dohvatit sve aktivnbosti iz baze koje su u blizini usera
    }

    val activityList: List<Activity> = listOf()
            Column {
                SearchBar(searchQuery.value) { newQuery ->
                    searchQuery.value = newQuery
                }
                Spacer(modifier = Modifier.height(8.dp))
                ShowLazyList(activityList.filter { activity ->
                    activity.sport.name?.contains(searchQuery.value, ignoreCase = true) == true
                })
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

