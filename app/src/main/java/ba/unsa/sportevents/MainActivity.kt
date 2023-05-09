package ba.unsa.sportevents

import SportEventsTheme
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import ba.unsa.sportevents.google_signin.GoogleAuthUiClient
import ba.unsa.sportevents.navigation.Navigation
import com.google.android.gms.auth.api.identity.Identity

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            SportEventsTheme {
                val lifecycleOwner = this
                Navigation(applicationContext, lifecycleOwner = lifecycleOwner)
            }
        }
    }
}
