package kz.bcm.b2b.presentation.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kz.bcm.b2b.presentation.ui.auth.LoginScreen
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.ui.auth.BeingTested
import kz.bcm.b2b.presentation.ui.auth.RegistrationScreen
import kz.bcm.b2b.presentation.ui.auth.RestoreCodeScreen
import kz.bcm.b2b.presentation.ui.card.CardSkin
import kz.bcm.b2b.presentation.ui.cart.CartSkin
import kz.bcm.b2b.presentation.ui.catalog.CatalogSkin
import kz.bcm.b2b.presentation.ui.compare.CompareSkin
import kz.bcm.b2b.presentation.ui.favorite.FavoriteSkin
import kz.bcm.b2b.presentation.ui.filter.FilterSkin
import kz.bcm.b2b.presentation.ui.navigationMenu.CatalogListSkin
import kz.bcm.b2b.presentation.ui.navigationMenu.ContactSkin
import kz.bcm.b2b.presentation.ui.navigationMenu.DeliveryPaymentSkin
import kz.bcm.b2b.presentation.ui.navigationMenu.PromotionSkin
import kz.bcm.b2b.presentation.ui.navigationMenu.ServiceCenter
import kz.bcm.b2b.presentation.ui.profile.ProfileSkin
import kz.bcm.b2b.presentation.ui.search.SearchSkin
import kz.bcm.b2b.presentation.ui.splash.SplashScreen
import org.koin.compose.KoinContext

@Composable
fun NavGraph(
    navController: NavHostController
) {

    KoinContext {
        NavHost(
            navController = navController,
            startDestination = Route.CATALOG.route
        ) {

            // Bottom Menu
            composable(Route.CATALOG.route) {
                CatalogSkin(navController)
            }

            composable("${Route.CATALOG.route}/{slug}") { backStackEntry ->
                val slug = backStackEntry.arguments?.getString("slug")

                CatalogSkin(navController, slug)
            }

            composable(Route.COMPARE.route) {
                CompareSkin(navController)
            }

            composable(Route.FAVORITE.route) {
                FavoriteSkin(navController)
            }

            composable(Route.CART.route) {
                CartSkin(navController)
            }

            composable(Route.PROFILE.route) {
                ProfileSkin(navController)
            }




            // Navigation Menu
            composable(Route.CATALOG_LIST.route) {
                CatalogListSkin(navController)
            }

            composable(Route.PROMOTION.route) {
                PromotionSkin()
            }

            composable(Route.DELIVERY_PAYMENT.route) {
                DeliveryPaymentSkin()
            }

            composable(Route.CONTACTS.route) {
                ContactSkin()
            }

            composable(Route.SERVICE_CENTER.route) {
                ServiceCenter()
            }




            // Other
            composable("${Route.CARD.route}/{slug}") { backStackEntry ->
                val slug = backStackEntry.arguments?.getString("slug")

                CardSkin(navController, slug)
            }

            composable(Route.SEARCH.route) {
                SearchSkin(navController)
            }

            composable(Route.FILTER.route) {
                FilterSkin(navController)
            }

            composable(Route.SPLASH.route) {
                SplashScreen()
            }




            // Auth
            composable(Route.LOGIN.route) {
                LoginScreen(navController)
            }

            composable(Route.REGISTRATION.route) {
                RegistrationScreen(navController)
            }

            composable(Route.RESTORE_CODE.route) {
                RestoreCodeScreen(navController)
            }

            composable(Route.BEING_TESTED.route) {
                BeingTested(navController)
            }

        }
    }
}