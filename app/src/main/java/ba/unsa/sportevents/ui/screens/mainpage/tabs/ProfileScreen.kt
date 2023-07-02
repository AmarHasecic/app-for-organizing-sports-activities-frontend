package ba.unsa.sportevents.ui.screens.mainpage.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ba.unsa.etf.R
import ba.unsa.sportevents.data.repository.DataRepository
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.data.repository.UserRepository
import ba.unsa.sportevents.ui.viewmodels.MainPageViewModel


@Composable
fun ProfileScreen(token : String, viewModel: MainPageViewModel) {

    val user = viewModel.user.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUser(token)
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
                    user.value?.let {
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
