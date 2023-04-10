package ba.unsa.sportevents.Composables

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun DatePicker(){

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Declaring a string value to store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mCalendar.set(mYear, mMonth, mDayOfMonth)
            mDate.value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(mCalendar.time)
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(350.dp)
                .height(45.dp)
                .border(BorderStroke(1.dp, color = Color.Black))
                .clickable(onClick = {
                    mDatePickerDialog.show()
                })
        ) {
            Text(
                text = if (mDate.value.isEmpty()) "dd/mm/yyyy" else mDate.value,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DatePicker()
}
