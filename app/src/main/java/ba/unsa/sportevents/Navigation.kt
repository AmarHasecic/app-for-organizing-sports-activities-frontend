package ba.unsa.sportevents

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ba.unsa.sportevents.login.LoginScreen


@Composable
fun Navigation(){

    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(
               route = Screen.LoginScreen.route){
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
    }

}