package ba.unsa.sportevents.ui.screens.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ba.unsa.etf.R
import ba.unsa.sportevents.ui.navigation.Screen
import ba.unsa.sportevents.ui.theme.MyFavGreen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

@Composable
fun LoginPage(
    navController: NavController,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Find your perfect match – in sports! Create events, connect with friends, and play together.",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))


                Button(
                    onClick = {
                        navController.navigate(Screen.RegisterEmailScreen.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(6.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.email),
                            contentDescription = "Email icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Sign up with email")
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .background(Color.Black)
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(11.dp))
                    Text(
                        text = "ALREADY A MEMBER",
                        fontSize = 10.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 3.dp, end = 15.dp)
                    )
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .background(Color.Black)
                            .weight(1f)
                    )
                }

                Button(
                    onClick = {
                        navController.navigate(Screen.LoginScreen.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MyFavGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Login",
                        color = Color.White
                    )
                }


            }
        }
    }
}

