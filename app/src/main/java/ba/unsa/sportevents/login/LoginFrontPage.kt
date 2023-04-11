package ba.unsa.sportevents.login


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ba.unsa.etf.R
import ba.unsa.sportevents.navigation.Screen
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

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        autoScrollLazyRow(items = images)
        Spacer(modifier = Modifier.height(5.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Find your perfect match – in sports! Create events, connect with friends, and play together.",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()

            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .height(50.dp)
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
                Text(text = "Continue with Google")
            }
            Spacer(modifier = Modifier.height(1.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.RegisterEmailScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .height(50.dp)
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
                Text(text = "Sign up with email")
            }
            Spacer(modifier = Modifier.height(60.dp))


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
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .height(50.dp),

                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFF2500),
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







