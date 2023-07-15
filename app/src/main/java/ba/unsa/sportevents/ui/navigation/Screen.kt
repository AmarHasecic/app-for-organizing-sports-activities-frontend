package ba.unsa.sportevents.ui.navigation

sealed class Screen (val route : String){

    object LoginFrontPage : Screen("LoginFrontPage")
    object LoginScreen : Screen("LoginScreen")
    object UserMainPage : Screen("UserMainPage")
    object RegisterEmailScreen : Screen("RegisterEmailScreen")
    object RegisterNamePassScreen : Screen("RegisterNamePassScreen")
    object RegisterDateScreen : Screen("RegisterDateScreen")
    object RegisterUsernameScreen : Screen("RegisterUsernameScreen")

    object ActivityDetails : Screen("ActivityDetails")
    object CreateActivity : Screen("CreateActivity")

    object SportsScreen : Screen("SportsScreen")
    object SearchPlaceScreen : Screen("SearchPlaceScreen")


}