package ba.unsa.sportevents.ui.screens.mainpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ba.unsa.etf.R
import ba.unsa.sportevents.data.model.SportActivity


@Composable
fun ActivityCard(sportActivity: SportActivity) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        backgroundColor = Color(0x1BE0E0E0),
        shape = RoundedCornerShape(7.dp),
        elevation = 4.dp
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.profile_picture),
                    contentDescription = "Profile photo",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
                if (sportActivity.user != null) {
                    Text(
                        text = sportActivity.user.fullName,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 8.dp),
                        color = Color.Black
                    )
                } else {
                    Text(
                        text = "Unknown user",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 8.dp),
                        color = Color.Black
                    )

                }
            }
            Text(
                text = sportActivity.title,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(top = 8.dp),
                color = Color.Black
            )
            Text(
                text = sportActivity.description,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 8.dp),
                color = Color.Black
            )
            Row(modifier = Modifier.padding(top = 16.dp)) {
                Button(
                    onClick = { /* Handle join button click */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFFF2500),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Join Activity")
                }
                Button(
                    onClick = { /* Handle details button click */ },
                    modifier = Modifier.padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFFF2500),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Details")
                }
            }
        }
    }
}
