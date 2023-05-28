package ba.unsa.sportevents.main_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ba.unsa.sportevents.model.Activity
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun ActivityDetails(activity: Activity) {

    Column() {
        Text(
            text = activity.host,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(8.dp)
        )
        Column(
            Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = activity.title,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(8.dp)
            )

            Text(
                text = activity.description,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(8.dp)
            )

        }
    }

    val activityPosition = LatLng(activity.location.latitude, activity.location.longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(activityPosition, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = activityPosition),
            title = activity.title,
            snippet = activity.description
        )
    }

}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    SportEventsTheme() {
        ActivityDetails();
    }
}
*/

