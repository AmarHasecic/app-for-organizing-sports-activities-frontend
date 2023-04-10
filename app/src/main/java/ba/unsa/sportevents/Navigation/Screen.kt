package ba.unsa.sportevents.Navigation

sealed class Screen (val route : String){

    object LoginFrontPage : Screen("LoginFrontPage")
    object LoginScreen : Screen("LoginScreen")
    object UserMainPage : Screen("UserMainPage")
}