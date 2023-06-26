package ba.unsa.sportevents.main_page


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import ba.unsa.sportevents.model.SportActivity


@Composable
fun ListActivities(cards: List<SportActivity>){

    LazyColumn {

        for (card in cards) {
            item {
                ActivityCard(card)
            }
        }
    }
}
