package kz.bcm.b2b.presentation.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.ui.card.CardScreen
import kz.bcm.b2b.presentation.ui.catalog.CatalogScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Route.CATALOG.name
    ) {

        composable(Route.CATALOG.name) {
            CatalogScreen(navController)
        }

        composable("${Route.CATALOG.name}/{slug}") { backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug")

            CatalogScreen(navController, slug)
        }

        composable(Route.COMPARE.name) {

        }

        composable(Route.FAVORITE.name) {

        }

        composable(Route.CART.name) {

        }

        composable(Route.PROFILE.name) {

        }


        composable("${Route.CARD.name}/{slug}") { backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug")

            CardScreen(navController, slug)
        }
    }
}