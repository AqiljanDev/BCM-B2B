package kz.bcm.b2b.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_catalog
import bcm_b2b.composeapp.generated.resources.ic_contact
import bcm_b2b.composeapp.generated.resources.ic_delivery
import bcm_b2b.composeapp.generated.resources.ic_logo
import bcm_b2b.composeapp.generated.resources.ic_logout
import bcm_b2b.composeapp.generated.resources.ic_sale
import bcm_b2b.composeapp.generated.resources.ic_service_center
import bcm_b2b.composeapp.generated.resources.inter_medium
import kotlinx.coroutines.launch
import kz.bcm.b2b.di.NavigationState
import kz.bcm.b2b.di.NavigationStateHolder
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.theme.ColorDarkRed
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.sharedPref.URL
import kz.bcm.b2b.sharedPref.putStringSharedPref
import kz.bcm.b2b.sharedPref.removeStringSharedPref
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun DrawerContent(navController: NavController) {

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Icon(
            painter = painterResource(Res.drawable.ic_logo),
            contentDescription = "logo",
            modifier = Modifier.size(130.dp),
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(5.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(18.dp),
            ) {
                DrawerItem(
                    resource = Res.drawable.ic_catalog,
                    title = "Каталог",
                    clickOn = { navController.navigate(Route.CATALOG_LIST.route) }
                )

                DrawerItem(
                    resource = Res.drawable.ic_sale,
                    title = "Акции",
                    clickOn = { navController.navigate(Route.PROMOTION.route) }
                )

                DrawerItem(
                    resource = Res.drawable.ic_delivery,
                    title = "Доставка и оплата",
                    clickOn = { navController.navigate(Route.DELIVERY_PAYMENT.route) }
                )

                DrawerItem(
                    resource = Res.drawable.ic_contact,
                    title = "Контакты",
                    clickOn = { navController.navigate(Route.CONTACTS.route) }
                )

                DrawerItem(
                    resource = Res.drawable.ic_service_center,
                    title = "Сервисный центр",
                    clickOn = { navController.navigate(Route.SERVICE_CENTER.route) }
                )
            }

            DrawerItem(
                resource = Res.drawable.ic_logout,
                title = "Выход",
                clickOn = {
                    coroutineScope.launch {
//                        removeStringSharedPref(URL.TOKEN.key)
                        putStringSharedPref(URL.TOKEN.key, "")

                        NavigationStateHolder.navigationState.emit(NavigationState.None)
                    }
                },
                tint = ColorDarkRed,
            )
        }
    }
}


@Composable
private fun DrawerItem(
    resource: DrawableResource,
    title: String,
    clickOn: () -> Unit,
    tint: Color = ColorMainGreen,
) {

    Row(
        modifier = Modifier.fillMaxWidth().clickable {
            clickOn()
        },
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(resource),
            contentDescription = "item",
            tint = tint,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = title,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium)),
        )
    }
}




