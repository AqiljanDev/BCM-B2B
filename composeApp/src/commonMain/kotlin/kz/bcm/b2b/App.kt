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
import kz.bcm.b2b.presentation.ui.splash.SplashScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val navigationState by NavigationStateHolder.navigationState.collectAsState()

    LaunchedEffect(navigationState) {
        when (navigationState) {
            is NavigationState.Normal -> {
                navController.navigate(Route.CATALOG.route) {
                    popUpTo(Route.SPLASH.route) {
                        inclusive = true
                    }
                    popUpTo(Route.LOGIN.route) {
                        inclusive = true
                    }
                }
            }
            is NavigationState.TokenExpired -> {
                navController.navigate(Route.LOGIN.route) {
                    popUpTo(Route.SPLASH.route) {
                        inclusive = true
                    }
                }
            }
            is NavigationState.Error -> {
                // Логика обработки ошибок, если необходима
                println("NavigationState Error: ${(navigationState as NavigationState.Error).message}")
            }

            else -> {
                navController.navigate(Route.SPLASH.route) {
                    popUpTo(Route.CATALOG.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    MyTheme {
        NavGraph(navController)
    }
}
