package kz.bcm.b2b

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import kz.bcm.b2b.di.NavigationState
import kz.bcm.b2b.di.NavigationStateHolder
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.theme.MyTheme
import kz.bcm.b2b.presentation.ui.main.NavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val navigationState by NavigationStateHolder.navigationState.collectAsState()

    LaunchedEffect(navigationState) {
        println("LaunchedEffect nav state")

        when (navigationState) {

            is NavigationState.Normal -> {
                println("NavigationState when: is normal expired")
                navController.navigate(Route.CATALOG.route) {
                    popUpTo(Route.SPLASH.route) {
                        inclusive = true
                        saveState = false
                    }
                    launchSingleTop = true
                }
            }

            is NavigationState.TokenExpired -> {
                println("NavigationState when: is token expired")
                navController.navigate(Route.LOGIN.route) {
                    popUpTo(Route.SPLASH.route) {
                        inclusive = true
                        saveState = false
                    }
                    launchSingleTop = true
                }
            }

            is NavigationState.Error -> {

                println("NavigationState when: Error: ${(navigationState as NavigationState.Error).message}")
            }

            else -> {
                println("NavigationState when: is none expired")
                navController.navigate(Route.SPLASH.route)
            }
        }
    }

    MyTheme {
        NavGraph(navController)
    }
}
