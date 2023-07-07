package ba.unsa.sportevents.ui.screens.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ba.unsa.sportevents.ui.viewmodels.MainPageViewModel


@Composable
fun CreateActivity(
    token: String,
    viewModel: MainPageViewModel
) {
    val sports = listOf("Football", "Basketball", "Volleyball")
    val user = viewModel.user.collectAsState()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var maxNumber by remember {mutableStateOf(0)}

    LaunchedEffect(Unit) {
        viewModel.getUser(token)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFFFF2500)
            ) {
                Text(
                    text = "Create an activity",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                )
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
            Text(
                text = "When?",
                color = Color.Black,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 10.dp)

            )


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


            Text(
                text = "How may people can join?",
                color = Color.Black,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 10.dp)
            )

            Box (modifier = Modifier.padding(horizontal = 10.dp)){


                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
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
                        modifier = Modifier.width((45.dp * 2) + 5.dp), // Set width to match the buttons
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


            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    // Handle button click
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
    }
}

