package kz.bcm.b2b.presentation.ui.main

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_cart
import bcm_b2b.composeapp.generated.resources.ic_catalog
import bcm_b2b.composeapp.generated.resources.ic_compare
import bcm_b2b.composeapp.generated.resources.ic_favorite
import bcm_b2b.composeapp.generated.resources.ic_profile
import kz.bcm.b2b.presentation.other.data.BottomItem
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.theme.ColorMainOrange
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = listOf(
        BottomItem("Каталог", Res.drawable.ic_catalog, Route.CATALOG.route),
        BottomItem("Сравнение", Res.drawable.ic_compare, Route.COMPARE.route),
        BottomItem("Избранное", Res.drawable.ic_favorite, Route.FAVORITE.route),
        BottomItem("Корзина", Res.drawable.ic_cart, Route.CART.route),
        BottomItem("Профиль", Res.drawable.ic_profile, Route.PROFILE.route)
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route


    BottomNavigation {

        items.forEachIndexed { index, item ->

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(item.img),
                        contentDescription = "Icon",
                        modifier = Modifier.size(25.dp)
                    )
                },
                unselectedContentColor = Color.LightGray,
                selectedContentColor = ColorMainOrange,
                label = {
                    Text(
                        text = item.title,
                        fontSize = 8.sp
                    )
                },
                selected = currentRoute == item.route,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    navController.navigate(item.route)
                    println("bottom navigation = current route: ${currentRoute}, item.route: ${item.route}")
                }
            )
        }
    }
}