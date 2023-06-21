package ba.unsa.sportevents.main_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ba.unsa.sportevents.main_page.tabs.TabContent
import ba.unsa.sportevents.main_page.tabs.TabItem
import ba.unsa.sportevents.main_page.tabs.Tabs
import ba.unsa.sportevents.model.User
import ba.unsa.sportevents.reusable.LocationPopUp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

private const val REQUEST_LOCATION_PERMISSION = 123


@OptIn(ExperimentalPagerApi::class)
@Composable
fun UserMainPage(token: String) {

    val list = listOf(TabItem.Home(token),TabItem.Map(token),TabItem.Profile(token), TabItem.Settings(token))
    val pagerState = rememberPagerState(initialPage = 0)

    LocationPopUp(requestPermission = REQUEST_LOCATION_PERMISSION)

    Column(modifier = Modifier.fillMaxSize()) {
        Tabs(tabs = list, pagerState = pagerState)
        TabContent(tabs = list, pagerState = pagerState, token = token)
    }

}
