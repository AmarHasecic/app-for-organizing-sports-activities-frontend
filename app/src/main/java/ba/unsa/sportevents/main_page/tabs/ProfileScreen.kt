package ba.unsa.sportevents.main_page.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ba.unsa.etf.R
import ba.unsa.sportevents.RetrofitInstance
import ba.unsa.sportevents.model.User
import kotlinx.coroutines.coroutineScope


@Composable
fun ProfileScreen(token : String) {

    var user by remember { mutableStateOf<User?>(null) }


    LaunchedEffect(Unit) {
        val retrievedUser = coroutineScope {
            RetrofitInstance.userApi.getUser("Bearer $token")
        }.body()

        if (retrievedUser != null) {
            user = retrievedUser
        }
    }

        Column(modifier = Modifier.fillMaxSize()) {

            Box(modifier = Modifier.weight(1f)) {
                Image(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    user?.let {
                        Text(
                            text = it.fullName,
                            style = MaterialTheme.typography.h5,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }

            Divider(modifier = Modifier.padding(horizontal = 16.dp))

        }
}
