package ba.unsa.sportevents.main_page

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ba.unsa.sportevents.model.SportActivity


@Composable
fun ActivityCard(sportActivity: SportActivity){

    Card(
        Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        backgroundColor = Color(0x12E0E0E0),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp) {


    }

}
