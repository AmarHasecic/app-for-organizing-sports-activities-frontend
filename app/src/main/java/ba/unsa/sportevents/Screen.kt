package ba.unsa.sportevents

sealed class Screen (val route : String){

    object LoginScreen : Screen("LoginScreen")
    object UserMainPage : Screen("UserMainPage")
}