package ba.unsa.sportevents

import ba.unsa.sportevents.ui.theme.SportEventsTheme
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import ba.unsa.etf.R
import ba.unsa.sportevents.ui.navigation.Navigation
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            SportEventsTheme {
                val lifecycleOwner = this
                Navigation(applicationContext, lifecycleOwner = lifecycleOwner)
            }
        }
    }
}
