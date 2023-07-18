package ba.unsa.sportevents.ui.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ba.unsa.sportevents.data.model.Location
import ba.unsa.sportevents.data.model.SportActivity
import ba.unsa.sportevents.data.model.User
import ba.unsa.sportevents.data.repository.ActivityRepository
import ba.unsa.sportevents.data.repository.DataRepository
import ba.unsa.sportevents.data.repository.UserRepository
import ba.unsa.sportevents.ui.screens.activity.ActivityDetails
import ba.unsa.sportevents.ui.screens.activity.CreateActivity
import ba.unsa.sportevents.ui.screens.activity.SearchPlaceScreen
import ba.unsa.sportevents.ui.screens.activity.SportsScreen
import ba.unsa.sportevents.ui.screens.login.LoginPage
import ba.unsa.sportevents.ui.screens.login.LoginScreen
import ba.unsa.sportevents.ui.screens.mainpage.UserMainPage
import ba.unsa.sportevents.ui.screens.register.RegisterFormDate
import ba.unsa.sportevents.ui.screens.register.RegisterFormEmail
import ba.unsa.sportevents.ui.screens.register.RegisterFormPass
import ba.unsa.sportevents.ui.screens.register.RegisterUsername
import ba.unsa.sportevents.ui.viewmodels.*
import java.net.URLEncoder
import java.time.LocalDateTime

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
    val activityDetailsViewModel: ActivityDetailsViewModel = remember {
        ActivityDetailsViewModel(activityRepository, userRepository)
    }

    val createActivityViewModel: CreateActivityViewModel = remember {
        CreateActivityViewModel(activityRepository, userRepository)
    }

    NavHost(navController = navController, startDestination = Screen.LoginFrontPage.route) {

        composable(
               route = Screen.LoginFrontPage.route){

               LoginPage(
                   navController = navController,
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
            entry.arguments?.getString("token")?.let { UserMainPage(navController,token = it, mainPageViewModel) }
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

        composable(
            route = Screen.ActivityDetails.route + "/{activity}/{token}",
            arguments = listOf(
                navArgument("activity"){
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("token"){
                    type = NavType.StringType
                    nullable = true
                }
            )
        ){
            entry ->
            val encodedActivity = entry.arguments?.getString("activity")?.let {
                URLEncoder.encode(it, "UTF-8")
            }
            if (encodedActivity != null) {

                val token = entry.arguments?.getString("token")

                token?.let {
                        ActivityDetails(sportActivity = encodedActivity, token = token, viewModel = activityDetailsViewModel )
                }

            }
        }

        composable(
            route = Screen.CreateActivity.route+ "/{token}",
            arguments = listOf(
                navArgument("token"){
                    type = NavType.StringType
                    nullable = true
                }
            )
        )
        { entry ->
            entry.arguments?.getString("token")
                ?.let {
                    val emptyLocation = Location(0.0, 0.0, "")
                    val emptyHost: User? = null

                    val currentDateTime = LocalDateTime.now()

                    val sportActivity = SportActivity(
                        id = "",
                        host = emptyHost,
                        title = "",
                        sport = "",
                        description = "",
                        location = emptyLocation,
                        startTime = currentDateTime.toString(),
                        date = currentDateTime.toString(),
                        numberOfParticipants = 0,
                        maxNumberOfParticipants = 0,
                        participants = emptyList()
                    )
                    CreateActivity(
                        token = it, navController = navController, sportActivity
                       )
                }
        }

        composable(
            route = Screen.SportsScreen.route+ "/{token}/{activity}",
            arguments = listOf(
                navArgument("token"){
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("activity"){
                    type = NavType.StringType
                    nullable = true
                }

            )
        )
        { entry ->
            val token = entry.arguments?.getString("token")
            val activity = entry.arguments?.getString("activity")

            token?.let {
                if (activity != null) {
                    SportsScreen(token = token, activity = activity, navController = navController)
                }
            }
        }

        composable(
            route = Screen.SearchPlaceScreen.route+ "/{token}/{activity}",
            arguments = listOf(
                navArgument("token"){
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("activity"){
                    type = NavType.StringType
                    nullable = true
                }

            )
        )
        { entry ->
            val token = entry.arguments?.getString("token")
            val activity = entry.arguments?.getString("activity")

            token?.let {
                if (activity != null) {
                    SearchPlaceScreen(token = token, viewModel = createActivityViewModel, navController = navController, activity = activity)
                }
            }
        }
    }
}
