package ba.unsa.sportevents.ui.screens.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ba.unsa.sportevents.data.model.Location
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.ui.components.formatToTime
import ba.unsa.sportevents.ui.components.makeToast
import ba.unsa.sportevents.ui.navigation.Screen
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

// Function to convert string to LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
fun parseToLocalDateTime(dateTimeString: String, pattern: String): LocalDateTime {

    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return LocalDateTime.parse(dateTimeString, formatter)
}


fun parseToDate(dateString: String, pattern: String): Date {
    val formatter = SimpleDateFormat(pattern)
    return formatter.parse(dateString)
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertDateToLocalDateTime(date: Date): LocalDateTime {
    val instant = date.toInstant()
    return instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateActivity(
    token: String,
    navController: NavController,
    activity: SportActivity
) {

    var title by remember { mutableStateOf(activity.title) }
    var description by remember { mutableStateOf(activity.description) }
    var maxNumber by remember {mutableStateOf(activity.maxNumberOfParticipants)}
    var mDate by remember { mutableStateOf(parseToLocalDateTime( activity.date, "yyyy-MM-dd")) }

    var eventTime by remember { mutableStateOf<Date?>(parseToDate(activity.startTime,"yyyy-MM-dd"))}

    val context = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mCalendar.set(mYear, mMonth, mDayOfMonth)
            mDate = mCalendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)
    )
    val timePickerFocusRequester = remember { FocusRequester() }



    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFFFF2500),
            ) {
                Text(
                    text = "Create an activity",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        },

        bottomBar = {
            Button(
                onClick = {

                    if(title == "" || description == "" || maxNumber == 0){
                        makeToast(context,"All fields are required")
                    }
                    else {
                        activity.title = title
                        activity.description = description
                        activity.maxNumberOfParticipants = maxNumber
                        activity.numberOfParticipants = 0
                        activity.startTime = convertDateToLocalDateTime(eventTime!!).toString()
                        activity.sport = ""
                        activity.date = mDate.toString()
                        activity.host = null
                        activity.location = Location(0.0,0.0,"")
                        activity.participants = emptyList()

                        val gson = Gson()
                        val jsonActivity : String = gson.toJson(activity)
                        navController.navigate("${Screen.SportsScreen.route}/${token}/${jsonActivity}")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFF2500),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Next")
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Give us some information about your activity",
                color = Color.Black,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .padding(horizontal = 5.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                       modifier = Modifier
                        .fillMaxWidth()
                    .padding(contentPadding),
                verticalArrangement = Arrangement.Center
            ) {

                item{
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text(text = "Title") },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(vertical = 10.dp)
                            .background(Color.White)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black
                        )
                    )
                }
                item {
                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text(text = "Description") },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(vertical = 10.dp)
                            .background(Color.White)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black
                        )
                    )
                }
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Divider(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .height(1.dp)
                                .fillMaxWidth(0.95f),
                            color = Color.LightGray
                        )
                    }
                }
                item {
                    Text(
                        text = "When?",
                        color = Color.Black,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .padding(horizontal = 10.dp)
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(2.dp))
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            text = "Date: ",
                            modifier = Modifier
                                .width(180.dp),
                            color = Color.Black
                        )

                        Text(
                            text = "Time: ",
                            modifier = Modifier
                                .width(100.dp),
                            color = Color.Black
                        )
                    }
                }

                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(180.dp)
                                .height(45.dp)
                                .border(BorderStroke(1.dp, color = Color.Black))
                                .clickable(onClick = {
                                    mDatePickerDialog.show()
                                })
                        ) {
                            Text(
                                text = mDate.toLocalDate().toString(),
                                modifier = Modifier.padding(10.dp),
                                color = Color.Black
                            )
                        }
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(45.dp)
                                .border(BorderStroke(1.dp, color = Color.Black))
                                .clickable(onClick = {
                                    timePickerFocusRequester.requestFocus()

                                    val calendar = Calendar.getInstance()
                                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                                    val minute = calendar.get(Calendar.MINUTE)

                                    val timePickerDialog = TimePickerDialog(
                                        context,
                                        { _, hourOfDay, minuteOfHour ->
                                            val selectedCalendar = Calendar.getInstance()
                                            selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                            selectedCalendar.set(Calendar.MINUTE, minuteOfHour)
                                            eventTime = selectedCalendar.time
                                        },
                                        hour,
                                        minute,
                                        false
                                    )
                                    timePickerDialog.show()
                                })
                                .focusRequester(timePickerFocusRequester)
                        ) {
                            Text(
                                text = eventTime?.formatToTime() ?: "00:00",
                                modifier = Modifier.padding(10.dp),
                                color = Color.Black
                            )
                        }
                    }
                }
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Divider(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .height(1.dp)
                                .fillMaxWidth(0.95f),
                            color = Color.LightGray
                        )
                    }
                }

                item {
                    Text(
                        text = "How many people can join?",
                        color = Color.Black,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .padding(horizontal = 10.dp)
                    )
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Button(
                            onClick = { if (maxNumber > 0) maxNumber-- },
                            modifier = Modifier.size(45.dp),
                            shape = RoundedCornerShape(7.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF2500)),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "−",
                                style = MaterialTheme.typography.h4,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        TextField(
                            value = maxNumber.toString(),
                            onValueChange = { maxNumber = it.toIntOrNull() ?: 0 },
                            modifier = Modifier.width((45.dp * 2) + 5.dp),
                            shape = RoundedCornerShape(7.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                textColor = Color.Black
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Button(
                            onClick = { if (maxNumber <= 50) maxNumber++ },
                            modifier = Modifier.size(45.dp),
                            shape = RoundedCornerShape(7.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF2500)),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "+",
                                style = MaterialTheme.typography.h4,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}



