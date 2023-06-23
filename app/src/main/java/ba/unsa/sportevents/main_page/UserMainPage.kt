package ba.unsa.sportevents.main_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import ba.unsa.sportevents.main_page.tabs.HomeScreen
import ba.unsa.sportevents.main_page.tabs.MapScreen
import ba.unsa.sportevents.main_page.tabs.ProfileScreen
import ba.unsa.sportevents.main_page.tabs.SettingsScreen
import ba.unsa.sportevents.reusable.LocationPopUp
import com.google.accompanist.pager.ExperimentalPagerApi

private const val REQUEST_LOCATION_PERMISSION = 123


@Composable
fun UserMainPage(token: String) {

    LocationPopUp(requestPermission = REQUEST_LOCATION_PERMISSION)
    var activeContent by remember { mutableStateOf(1) }


    Scaffold(
        topBar = {

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
                        selected = activeContent == 1,
                        onClick = {
                            activeContent = 1
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
                        selected = activeContent == 2,
                        onClick = {
                            activeContent = 2
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
                        selected = activeContent == 3,
                        onClick = {
                            activeContent = 3
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
                        selected = activeContent == 4,
                        onClick = {
                            activeContent = 4
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
                1 -> HomeScreen(token)
                2 -> MapScreen(token)
                3 -> ProfileScreen(token)
                4 -> SettingsScreen(token)
            }
        }
    }
}
