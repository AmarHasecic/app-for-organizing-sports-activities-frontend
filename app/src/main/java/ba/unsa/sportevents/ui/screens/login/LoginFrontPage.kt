package ba.unsa.sportevents.ui.screens.login

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import ba.unsa.etf.R
import ba.unsa.sportevents.ui.components.CustomButton
import ba.unsa.sportevents.ui.navigation.Screen

@Composable
fun LoginPage(
    navController: NavController,
) {
    val context = LocalContext.current
    // Request location permission
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ){}

    LaunchedEffect(Unit){
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission not granted, request it
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )

        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 15.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.logincover),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                    )
                }
                Text(
                    text = "Find your perfect match – in sports! Create events, connect with friends, and play together.",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        navController.navigate(Screen.RegisterEmailScreen.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 20.dp)
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
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

                CustomButton(text = "Login") {
                    navController.navigate(Screen.LoginScreen.route)
                }

            }
        }
    }
}

