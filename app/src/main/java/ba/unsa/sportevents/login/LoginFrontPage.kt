package ba.unsa.sportevents.login


import android.widget.Toast
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
import ba.unsa.sportevents.composables.autoScrollLazyRow
import ba.unsa.sportevents.google_signin.SignInState
import ba.unsa.sportevents.navigation.Screen
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun LoginPage(
    navController: NavController,
    state: SignInState,
    onSignInClick: () -> Unit

    ) {

    //error handling
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }


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
                onClick = onSignInClick,
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

               Box(
                   contentAlignment = Alignment.CenterStart,
                ) {

                    Image(
                        painter = painterResource(R.drawable.google_icon),
                        contentDescription = "Google icon",
                        modifier = Modifier.size(24.dp)
                    )
                   Box(
                       modifier = Modifier
                           .fillMaxWidth(),
                       contentAlignment = Alignment.Center,
                   ) {

                       Text(
                           text = "Continue with Google"
                       )
                   }
                }

            }
            Spacer(modifier = Modifier.height(1.dp))

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

                Box(
                    contentAlignment = Alignment.CenterStart,
                ) {

                    Image(
                        painter = painterResource(R.drawable.email),
                        contentDescription = "Google icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {

                        Text(
                            text = "Sign up with email"
                        )
                    }
                }

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







