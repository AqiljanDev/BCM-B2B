package kz.bcm.b2b.presentation.ui.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_medium
import org.jetbrains.compose.resources.Font
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorMunsell
import kz.bcm.b2b.presentation.viewmodel.ProfileViewModel
import org.koin.compose.koinInject


@Composable
fun ProfileScreen(navController: NavHostController) {
    val viewModel: ProfileViewModel = koinInject()

    val stateOrder = viewModel.order.collectAsState()
    val stateOrderFindOne = viewModel.orderFindOne.collectAsState()
    val stateUserDiscount = viewModel.userDiscount.collectAsState()
    val stateCart = viewModel.cart.collectAsState()

    val stateCabinet = viewModel.cabinet.collectAsState()
    val stateBills = viewModel.bills.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.getOrder()
        viewModel.getUserDiscount()
        viewModel.getMiniCart()
        viewModel.getCabinet()
        viewModel.getBills()
    }

    LaunchedEffect(stateOrderFindOne) {
        println("ProfileScreen: state order find one: ${stateOrderFindOne.value}")
    }


    val items =
        listOf(ProfileItems.ORDERS, ProfileItems.DATA, ProfileItems.PASSWORD, ProfileItems.EXIT)

    var stateActive by remember {
        mutableStateOf(ProfileItems.ORDERS)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {

            items(items = items) { item ->

                Text(
                    text = item.title,
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium)),
                    color = if (stateActive == item) Color.White else Color.Black,
                    modifier = Modifier
                        .clip(RoundedCornerShape(7.dp))
                        .background(
                            color = if (stateActive == item) ColorMainGreen else ColorMunsell
                        ).padding(horizontal = 17.dp, vertical = 7.dp)
                        .clickable(
                            indication = null,
                            interactionSource = MutableInteractionSource()
                        ) {
                            stateActive = item
                        }
                )
            }
        }

        AnimatedVisibility(visible = stateActive == ProfileItems.ORDERS) {
            OrdersItem(
                orders = stateOrder.value,
                clickEye = { id ->
                    viewModel.getFindOneOrder(id)
                    stateActive = ProfileItems.ORDER_DETAILS
                }
            )
        }

        AnimatedVisibility(visible = stateActive == ProfileItems.DATA) {
            DataItem(
                cabinet = stateCabinet.value,
                bills = stateBills.value,
                updateCabinet = { cabinet -> viewModel.updateCabinet(cabinet) },
                createBill = { body -> viewModel.createBills(body) },
                updateBill = { id, body -> viewModel.updateBills(id, body) },
                deleteBill = { id -> viewModel.deleteBills(id) }
            )
        }


        AnimatedVisibility(visible = stateActive == ProfileItems.PASSWORD) {
            Text(stateActive.title)
        }


        AnimatedVisibility(visible = stateActive == ProfileItems.EXIT) {
            Text(stateActive.title)
        }


        AnimatedVisibility(visible = stateActive == ProfileItems.ORDER_DETAILS) {
            OrdersItemDetails(
                order = stateOrderFindOne.value,
                cartMini = stateCart.value,
                clickEvent = { item -> viewModel.eventCart(item) },
                clickDelete = { id -> viewModel.deleteCart(id) }
            )
        }

    }
}




private enum class ProfileItems(val title: String) {
    ORDERS("Заказы"),
    DATA("Данные"),
    PASSWORD("Пароль"),
    EXIT("Выход"),
    ORDER_DETAILS("Детали заказа")
}
