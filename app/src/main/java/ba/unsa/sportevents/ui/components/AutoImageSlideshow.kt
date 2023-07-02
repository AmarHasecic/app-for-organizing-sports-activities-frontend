package ba.unsa.sportevents.reusable

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun autoScrollLazyRow(
    modifier: Modifier = Modifier,
    items: List<Int>,
    slowScrollSpeed: Float = 0.00f, // fraction of image width
    fastScrollSpeed: Float = 2.9f, // fraction of image width
    delayBetweenScrolls: Long = 5000 // delay in milliseconds between scrolls
) {
    val scrollState = rememberLazyListState()

    // Coroutine to update the scroll position
    LaunchedEffect(Unit) {
        delay(5000)
        var currentIndex = 0
        while (true) {
            val currentScrollSpeed = if (System.currentTimeMillis() - scrollState.layoutInfo.viewportEndOffset > delayBetweenScrolls) fastScrollSpeed else slowScrollSpeed
            val scrollDistance = with(LocalDensity) { ImageSizeWidth } * currentScrollSpeed
            scrollState.animateScrollBy(scrollDistance.value)
            delay(5000)

            if (scrollState.firstVisibleItemIndex == currentIndex + 1) {
                // The next image has come into view, reset the index and scroll speed
                currentIndex++
                scrollState.animateScrollToItem(index = currentIndex)
            }
            else if (scrollState.firstVisibleItemIndex > currentIndex + 1) {
                // We have overshot the next image, reset the index and scroll speed
                currentIndex = scrollState.firstVisibleItemIndex
                scrollState.animateScrollToItem(index = currentIndex)
            }

            if (currentIndex == items.lastIndex) {
                // We have reached the end of the list, reset to the beginning
                currentIndex = 0
                scrollState.animateScrollToItem(index = currentIndex)
                delay(5000)
            }

        }
    }

    LazyRow(
        modifier = Modifier
            .padding(0.dp),
        state = scrollState
    ) {
        items(items) { item ->
            Image(
                painter = painterResource(item),
                contentDescription = null,
                modifier = Modifier.size(ImageSizeWidth, ImageSizeHeight)
                    .padding(0.dp)
            )
        }
    }
}

private val ImageSizeWidth = 400.dp
private val ImageSizeHeight = 400.dp
