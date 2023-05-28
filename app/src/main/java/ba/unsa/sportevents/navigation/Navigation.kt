package ba.unsa.sportevents.navigation

import android.app.Activity.RESULT_OK
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ba.unsa.sportevents.main_page.UserMainPage
import ba.unsa.sportevents.google_signin.GoogleAuthUiClient
import ba.unsa.sportevents.google_signin.SignInViewModel
import ba.unsa.sportevents.login.LoginPage
import ba.unsa.sportevents.login.LoginScreen
import ba.unsa.sportevents.register.RegisterFormDate
import ba.unsa.sportevents.register.RegisterFormEmail
import ba.unsa.sportevents.register.RegisterFormPass
import ba.unsa.sportevents.register.RegisterUsername
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import java.net.URLEncoder

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    context: Context,
    lifecycleOwner: LifecycleOwner
){
    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = Screen.LoginFrontPage.route) {

        composable(
               route = Screen.LoginFrontPage.route){

            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val googleAuthUiClient by lazy {
                GoogleAuthUiClient(
                    context = context,
                    oneTapClient = Identity.getSignInClient(context)
                )
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if(result.resultCode == RESULT_OK) {
                        lifecycleOwner.lifecycleScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if(state.isSignInSuccessful) {
                    Toast.makeText(
                        context,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()
/*   Perform server request for all data linked to a user with that email or create a new user if email not in database
                    navController.navigate("${Screen.UserMainPage.route}/${"bravo"}")
                    viewModel.resetState()
*/


                }
            }

               LoginPage(
                   navController = navController,
                   state = state,
                   onSignInClick = {
                       lifecycleOwner.lifecycleScope.launch {
                           val signInIntentSender = googleAuthUiClient.signIn()
                           launcher.launch(
                               IntentSenderRequest.Builder(
                                   signInIntentSender ?: return@launch
                               ).build()
                           )
                       }
                   }
               )

        }

        composable(
            route = Screen.LoginScreen.route
        ){
            LoginScreen(navController = navController)
        }

        composable(
                   route = Screen.UserMainPage.route + "/{token}",
                   arguments = listOf(
                       navArgument("token"){
                           type = NavType.StringType
                           nullable = true
                       }
                   )
            )
        { entry ->
            entry.arguments?.getString("token")?.let { UserMainPage(token = it) }
        }

        composable(
            route = Screen.RegisterEmailScreen.route
        )
        {
            RegisterFormEmail(navController = navController)
        }

        composable(
            route = Screen.RegisterNamePassScreen.route+ "/{user}",
            arguments = listOf(
                navArgument("user"){
                    type = NavType.StringType
                    nullable = true
                }
            )
        )
        { entry ->
            val encodedUser = entry.arguments?.getString("user")?.let {
                URLEncoder.encode(it, "UTF-8")
            }
            RegisterFormPass(navController = navController, user = encodedUser)
        }

        composable(
            route = Screen.RegisterDateScreen.route + "/{user}",
            arguments = listOf(
                navArgument("user"){
                    type = NavType.StringType
                    nullable = true
                }
            )
        )
        { entry ->
            val encodedUser = entry.arguments?.getString("user")?.let {
                URLEncoder.encode(it, "UTF-8")
            }
            RegisterFormDate(navController = navController, user = encodedUser)
        }

        composable(
            route = Screen.RegisterUsernameScreen.route + "/{user}",
            arguments = listOf(
                navArgument("user"){
                    type = NavType.StringType
                    nullable = true
                }
            )
        )
        { entry ->
            val encodedUser = entry.arguments?.getString("user")?.let {
                URLEncoder.encode(it, "UTF-8")
            }
            RegisterUsername(navController = navController, user = encodedUser)
        }

    }

}