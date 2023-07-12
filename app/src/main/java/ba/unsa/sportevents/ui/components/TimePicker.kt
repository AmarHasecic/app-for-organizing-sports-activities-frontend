package ba.unsa.sportevents.ui.components

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ShowTimePicker(
    context: Context,
    onTimeSelected: (hour: Int, minute: Int) -> Unit
) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(
        context,
        { _, hourOfDay, minuteOfHour ->
            onTimeSelected(hourOfDay, minuteOfHour)
        },
        hour,
        minute,
        false
    ).show()
}

fun Date.formatToTime(): String {
    val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return format.format(this)
}