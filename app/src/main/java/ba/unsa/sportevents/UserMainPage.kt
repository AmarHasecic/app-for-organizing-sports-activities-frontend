package ba.unsa.sportevents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ba.unsa.sportevents.model.User
import kotlinx.coroutines.coroutineScope
import java.net.URLDecoder


@Composable
fun UserMainPage(token: String) {
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
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
            Text(
                text = user.toString(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )

    }
}
