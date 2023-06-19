package ba.unsa.sportevents.main_page.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ba.unsa.sportevents.RetrofitInstance
import ba.unsa.sportevents.main_page.ActivityCard
import ba.unsa.sportevents.model.User
import kotlinx.coroutines.coroutineScope


@Composable
fun ProfileScreen(token : String){

    var user by remember { mutableStateOf<User?>(null) }


    LaunchedEffect(Unit) {
        val retrievedUser = coroutineScope {
            RetrofitInstance.userApi.getUser("Bearer $token")
        }.body()

        if (retrievedUser != null) {
            user = retrievedUser
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        user?.let { user ->
            Text(
                text = "Full Name: ${user.fullName}",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Email: ${user.email}",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Username: ${user.username}",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Date of Birth: ${user.dateOfBirth}",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Sports:",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            LazyRow {
                items(user.sports) { sport ->
                    Card(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        elevation = 4.dp
                    ) {
                        Text(
                            text = sport,
                            modifier = Modifier.padding(8.dp),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Activities:",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            LazyColumn {
                items(user.activities) { activity ->
                   
                    ActivityCard(activity = activity)
                }
            }
        }
    }

}