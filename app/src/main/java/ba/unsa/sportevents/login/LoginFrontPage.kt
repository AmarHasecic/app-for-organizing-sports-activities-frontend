package ba.unsa.sportevents.login


import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ba.unsa.etf.R
import ba.unsa.sportevents.composables.autoScrollLazyRow


@Composable
fun LoginPage(navController: NavController) {

    val images = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5
    )
    autoScrollLazyRow(items = images)
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SportEventsTheme {
        LoginPage()
    }
}
*/







