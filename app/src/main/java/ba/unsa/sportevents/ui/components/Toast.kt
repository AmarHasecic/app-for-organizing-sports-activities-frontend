package ba.unsa.sportevents.ui.components

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast


fun makeToast(context: Context, message: String){

    val handler = Handler(Looper.getMainLooper())
    handler.post {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}