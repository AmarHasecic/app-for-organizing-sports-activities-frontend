package ba.unsa.sportevents.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ba.unsa.sportevents.UserMainPage
import ba.unsa.sportevents.login.LoginPage
import ba.unsa.sportevents.login.LoginScreen
import ba.unsa.sportevents.register.RegisterFormEmal


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(){

    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = Screen.LoginFrontPage.route) {
        composable(
               route = Screen.LoginFrontPage.route){
               LoginPage(navController = navController)
        }

        composable(
            route = Screen.LoginScreen.route
        ){
            LoginScreen(navController = navController)
        }

        composable(
                   route = Screen.UserMainPage.route + "/{username}",
                   arguments = listOf(
                       navArgument("username"){
                           type = NavType.StringType
                           nullable = true
                       }
                   )
            )
        { entry ->
            UserMainPage(user = entry.arguments?.getString("username"))
        }

        composable(
            route = Screen.RegisterEmailScreen.route+ "/{user}",
                    arguments = listOf(
                    navArgument("user"){
                        type = NavType.StringType
                        nullable = true
                    }
                    )
        )
        { entry ->
            RegisterFormEmal(user = entry.arguments?.getString("user"))
        }

    }

}