package ba.unsa.sportevents.navigation

sealed class Screen (val route : String){

    object LoginFrontPage : Screen("LoginFrontPage")
    object LoginScreen : Screen("LoginScreen")
    object UserMainPage : Screen("UserMainPage")
    object RegisterEmailScreen : Screen("RegisterEmailScreen")
}