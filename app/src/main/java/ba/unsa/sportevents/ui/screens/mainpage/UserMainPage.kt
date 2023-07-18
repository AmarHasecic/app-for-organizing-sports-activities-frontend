package ba.unsa.sportevents.ui.screens.mainpage

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ba.unsa.sportevents.data.repository.DataRepository
import ba.unsa.sportevents.data.repository.UserRepository
import ba.unsa.sportevents.ui.screens.mainpage.tabs.HomeScreen
import ba.unsa.sportevents.ui.screens.mainpage.tabs.MapScreen
import ba.unsa.sportevents.ui.screens.mainpage.tabs.ProfileScreen
import ba.unsa.sportevents.ui.screens.mainpage.tabs.SettingsScreen
import ba.unsa.sportevents.gps.LocationPopUp
import ba.unsa.sportevents.ui.viewmodels.MainPageViewModel

private const val REQUEST_LOCATION_PERMISSION = 123


@Composable
fun UserMainPage(navController: NavController, token: String, viewModel: MainPageViewModel) {

    LocationPopUp(requestPermission = REQUEST_LOCATION_PERMISSION)
    var activeContent by remember { mutableStateOf(0) }



    Scaffold(
        topBar = {

            TopAppBar(
                backgroundColor = Color(0xFFFF2500),
            ) {

                when (activeContent) {
                    0 -> Text(text = "Home",  modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold))
                    1 -> Text(text = "Map",  modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold))
                    2 -> Text(text = "Profile",  modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold))
                    3 -> Text(text = "Settings",  modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold))
                }
            }

        },

        bottomBar = {
            BottomAppBar(
                backgroundColor = Color.White
            ) {
                TabRow(
                    selectedTabIndex = activeContent ,
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color.White


                ) {
                    LeadingIconTab(
                        selected = activeContent == 0,
                        onClick = {
                            activeContent = 0
                        },
                        text = { Text("") },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = null
                            )
                        },
                        selectedContentColor = Color(0xFFFF2500),
                        unselectedContentColor = Color.LightGray,
                        enabled = true
                    )

                    LeadingIconTab(
                        selected = activeContent == 1,
                        onClick = {
                            activeContent = 1
                        },
                        text = { Text("") },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null
                            )
                        },
                        selectedContentColor = Color(0xFFFF2500),
                        unselectedContentColor = Color.LightGray,
                        enabled = true
                    )

                    LeadingIconTab(
                        selected = activeContent == 2,
                        onClick = {
                            activeContent = 2
                        },
                        text = { Text("") },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null
                            )
                        },
                        selectedContentColor = Color(0xFFFF2500),
                        unselectedContentColor = Color.LightGray,
                        enabled = true
                    )

                    LeadingIconTab(
                        selected = activeContent == 3,
                        onClick = {
                            activeContent = 3
                        },
                        text = { Text("") },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null
                            )
                        },
                        selectedContentColor = Color(0xFFFF2500),
                        unselectedContentColor = Color.LightGray,
                        enabled = true
                    )
                }
            }
        }


    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {

            when (activeContent) {
                0 -> HomeScreen(navController,token,viewModel)
                1 -> MapScreen(token,viewModel)
                2 -> ProfileScreen(navController,token,viewModel)
                3 -> SettingsScreen(token,viewModel)
            }
        }
    }
}
