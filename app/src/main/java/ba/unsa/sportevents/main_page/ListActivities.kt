package ba.unsa.sportevents.main_page


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import ba.unsa.sportevents.model.Activity


@Composable
fun ListActivities(cards: List<Activity>){

    LazyColumn {

        for (card in cards) {
            item {
                ActivityCard(card)
            }
        }
    }
}
