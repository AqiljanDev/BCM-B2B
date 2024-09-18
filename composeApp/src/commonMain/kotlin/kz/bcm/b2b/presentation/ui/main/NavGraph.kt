package kz.bcm.b2b.presentation.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.ui.card.CardScreen
import kz.bcm.b2b.presentation.ui.cart.CartScreen
import kz.bcm.b2b.presentation.ui.catalog.CatalogScreen
import kz.bcm.b2b.presentation.ui.compare.CompareScreen
import kz.bcm.b2b.presentation.ui.favorite.FavoriteScreen
import kz.bcm.b2b.presentation.ui.profile.ProfileScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Route.CATALOG.route
    ) {

        composable(Route.CATALOG.route) {
            CatalogScreen(navController)
        }

        composable("${Route.CATALOG.route}/{slug}") { backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug")

            CatalogScreen(navController, slug)
        }

        composable(Route.COMPARE.route) {
            CompareScreen(navController)
        }

        composable(Route.FAVORITE.route) {
            FavoriteScreen(navController)
        }

        composable(Route.CART.route) {
            CartScreen(navController)
        }

        composable(Route.PROFILE.route) {
            ProfileScreen(navController)
        }


        composable("${Route.CARD.route}/{slug}") { backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug")

            CardScreen(navController, slug)
        }
    }
}