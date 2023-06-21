package ba.unsa.sportevents.main_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ba.unsa.sportevents.model.Activity


@Composable
fun ActivityCard(activity: Activity){

    Card(
        Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        backgroundColor = Color(0x12E0E0E0),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp) {

        Column() {
            Text(
                text = activity.host,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(8.dp)
            )
       Column(
           Modifier
               .padding(10.dp)
               .fillMaxWidth()
       ) {
           Text(
               text = activity.title,
               style = MaterialTheme.typography.caption,
               modifier = Modifier.padding(8.dp)
           )

           Text(
               text = activity.description,
               style = MaterialTheme.typography.body1,
               modifier = Modifier.padding(8.dp)
           )

          }
        }
    }

}
