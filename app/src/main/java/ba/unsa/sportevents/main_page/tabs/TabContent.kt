package ba.unsa.sportevents.main_page.tabs

import androidx.compose.runtime.Composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContent(tabs:List<TabItem>,pagerState: PagerState, token : String) {
    HorizontalPager(count = tabs.size,state=pagerState) { page ->
        tabs[page].screens(token)

    }
}