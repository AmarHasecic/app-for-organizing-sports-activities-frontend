package ba.unsa.sportevents.main_page.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

typealias ComposableFun = @Composable (token : String)->Unit

sealed class TabItem(val title:String,val icons:ImageVector, val screens:ComposableFun){

data class Home(val token: String) : TabItem(
    title = "",
    icons = Icons.Default.Home,
    screens = { HomeScreen(token) }
)

data class Map(val token: String) : TabItem(
    title = "",
    icons = Icons.Default.LocationOn,
    screens = { MapScreen(token) }
)

data class Profile(val token: String) : TabItem(
    title = "",
    icons = Icons.Default.AccountCircle,
    screens = { ProfileScreen(token) }
)

data class Settings(val token: String) : TabItem(
    title = "",
    icons = Icons.Default.Settings,
    screens = { SettingsScreen(token) }
)


}