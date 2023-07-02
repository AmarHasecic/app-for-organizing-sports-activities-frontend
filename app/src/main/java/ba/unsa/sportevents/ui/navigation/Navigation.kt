package ba.unsa.sportevents.ui.navigation

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
import androidx.compose.runtime.remember
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ba.unsa.sportevents.ui.screens.mainpage.UserMainPage
import ba.unsa.sportevents.data.network.google_signin.GoogleAuthUiClient
import ba.unsa.sportevents.data.network.google_signin.SignInViewModel
import ba.unsa.sportevents.data.repository.ActivityRepository
import ba.unsa.sportevents.data.repository.DataRepository
import ba.unsa.sportevents.data.repository.UserRepository
import ba.unsa.sportevents.ui.screens.login.LoginPage
import ba.unsa.sportevents.ui.screens.login.LoginScreen
import ba.unsa.sportevents.ui.screens.register.RegisterFormDate
import ba.unsa.sportevents.ui.screens.register.RegisterFormEmail
import ba.unsa.sportevents.ui.screens.register.RegisterFormPass
import ba.unsa.sportevents.ui.screens.register.RegisterUsername
import ba.unsa.sportevents.ui.viewmodels.LoginFormViewModel
import ba.unsa.sportevents.ui.viewmodels.MainPageViewModel
import ba.unsa.sportevents.ui.viewmodels.RegisterViewModel
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

    //repos
    val userRepository = UserRepository(apiService = DataRepository.userApiService)
    val activityRepository = ActivityRepository(apiService = DataRepository.activitiesApiService)

    //view models
    val loginViewModel: LoginFormViewModel = remember {
        LoginFormViewModel(userRepository)
    }
    val mainPageViewModel: MainPageViewModel = remember {
        MainPageViewModel(userRepository, activityRepository)
    }
    val registerViewModel: RegisterViewModel = remember {
        RegisterViewModel(userRepository)
    }

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
            LoginScreen(navController = navController, loginViewModel)
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
            entry.arguments?.getString("token")?.let { UserMainPage(token = it, mainPageViewModel) }
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
            RegisterUsername(navController = navController, user = encodedUser, viewModel = registerViewModel)
        }

    }

}