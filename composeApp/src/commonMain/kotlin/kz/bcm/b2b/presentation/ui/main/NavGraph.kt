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
import kz.bcm.b2b.presentation.ui.filterFull.FilterFullSkin
import kz.bcm.b2b.presentation.ui.navigationMenu.catalogList.CatalogListSkin
import kz.bcm.b2b.presentation.ui.navigationMenu.contact.ContactSkin
import kz.bcm.b2b.presentation.ui.navigationMenu.delivery.DeliveryPaymentSkin
import kz.bcm.b2b.presentation.ui.navigationMenu.promotion.PromotionSkin
import kz.bcm.b2b.presentation.ui.navigationMenu.service.ServiceCenterSkin
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
            startDestination = Route.SPLASH.route
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
            composable("${Route.CATALOG_LIST.route}/{slug}") { backStackEntry ->
                val slug = backStackEntry.arguments?.getString("slug")

                CatalogListSkin(navController, slug)
            }

            composable(Route.CATALOG_LIST.route) {
                CatalogListSkin(navController, null)
            }

            composable(Route.PROMOTION.route) {
                PromotionSkin(navController)
            }

            composable(Route.DELIVERY_PAYMENT.route) {
                DeliveryPaymentSkin(navController)
            }

            composable(Route.CONTACTS.route) {
                ContactSkin(navController)
            }

            composable(Route.SERVICE_CENTER.route) {
                ServiceCenterSkin(navController)
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

            composable("${Route.FILTER_FULL.route}/{category}/{slug}/{sort}/{f}") { backStackEntry ->
                val category: String = backStackEntry.arguments?.getString("category") ?: "Каталог"
                val slug: String = backStackEntry.arguments?.getString("slug") ?: "index"
                val sort: String = backStackEntry.arguments?.getString("sort") ?: "new"
                val f: String = backStackEntry.arguments?.getString("f").orEmpty()

                println("active full filter = category: $category, slug: $slug, sort: $sort, f: $f")

                FilterFullSkin(
                    navController = navController,
                    category = category,
                    slug = slug,
                    sort = sort,
                    f = f
                )
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