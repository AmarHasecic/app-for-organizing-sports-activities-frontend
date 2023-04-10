package ba.unsa.sportevents.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ba.unsa.sportevents.UserMainPage
import ba.unsa.sportevents.login.LoginPage
import ba.unsa.sportevents.login.LoginScreen


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
    }

}