package kz.bcm.b2b.presentation.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_camera_off
import bcm_b2b.composeapp.generated.resources.ic_cart
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import bcm_b2b.composeapp.generated.resources.inter_regular
import bcm_b2b.composeapp.generated.resources.oswald_bold
import bcm_b2b.composeapp.generated.resources.oswald_medium
import kz.bcm.b2b.data.dto.cart.event.PostCartDto
import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.data.cart.mini.Product
import kz.bcm.b2b.domain.data.order.findOne.FindOneOrderProduct
import kz.bcm.b2b.domain.data.order.findOne.FindOneProduct
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorPlatinum
import kz.bcm.b2b.presentation.ui.catalog.formatPrice
import kz.bcm.b2b.presentation.ui.catalog.getIdFromCartMini
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun OrdersItemDetails(
    order: FindOneProduct,
    cartMini: GetCartMini,
    clickEvent: (item: PostCart) -> Unit,
    clickDelete: (id: Int) -> Unit
) {
    val outScroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(outScroll),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(ColorPlatinum)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Заказ #${order.id}",
                    fontSize = 17.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_bold))
                )

                Text(
                    text = order.orderStatus.name,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium))
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OrderCharacter(name = "Дата", value = order.date)
                OrderCharacter(name = "Бил", value = order.userBill.bank)
                OrderCharacter(
                    name = "Доставка",
                    value = if (order.deliverId == 1) "Самовывоз" else "Доставка"
                )
                OrderCharacter(name = "Адрес", value = order.address)
            }

            OrderCharacter(name = "Администратор", value = order.admins?.email ?: "Не определен")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Сумма: ${formatPrice(order.products.sumOf { it.count * it.price })} ₸",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.oswald_bold))
                )

                Text(
                    text = "Повторить заказ",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium)),
                    color = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(ColorMainGreen)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .clickable(
                            indication = null,
                            interactionSource = MutableInteractionSource()
                        ) {
                            order.products.forEach {

                                if (cartMini.products.find { prods -> prods.prodId == it.id1c } == null) {
                                    clickEvent(
                                        PostCartDto(
                                            prodId = it.id1c,
                                            count = 1
                                        )
                                    )
                                    println("order product = $it")
                                }

                            }
                        }
                )
            }
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 20_000.dp)
                .nestedScroll(
                    connection = object : NestedScrollConnection {
                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {
                            if (outScroll.canScrollForward && available.y < 0) {
                                val consumed = outScroll.dispatchRawDelta(-available.y)
                                return Offset(x = 0f, y = -consumed)
                            }
                            return Offset.Zero
                        }
                    }
                ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(order.products) { prod ->

                OrderDetailsProduct(
                    product = prod,
                    cartMini = cartMini.products,
                    clickEvent = clickEvent,
                    clickDelete = clickDelete
                )
            }
        }

    }
}


@Composable
fun OrderDetailsProduct(
    product: FindOneOrderProduct,
    cartMini: List<Product>,
    clickEvent: (item: PostCart) -> Unit,
    clickDelete: (id: Int) -> Unit
) {
    var stateCartAdded by remember {
        mutableStateOf(
            cartMini.any { it.prodId == product.id1c }
        )
    }


    LaunchedEffect(product, cartMini) {
        stateCartAdded = cartMini.any { it.prodId == product.id1c }
    }


    Column(
        modifier = Modifier.fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(Res.drawable.ic_camera_off),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.title,
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium))
                )

                Text(
                    text = "Арт.: ${product.article} - Скидка: ${product.discount}",
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_regular))
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${formatPrice(product.price)} ₸ - ${product.count}шт",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(Res.font.oswald_medium))
            )

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = if (stateCartAdded) Color.Black else ColorMainGreen
                    )
                    .padding(horizontal = 10.dp, vertical = 7.dp)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) {
                        stateCartAdded = !stateCartAdded

                        if (stateCartAdded) {
                            clickEvent(
                                PostCartDto(prodId = product.id1c, count = 1)
                            )
                        } else {
                            clickDelete(
                                getIdFromCartMini(cartMini, product.id1c)
                            )
                        }
                    },
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_cart),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = if (stateCartAdded) "Убрать" else "Добавить",
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_regular)),
                    color = Color.White
                )
            }
        }

    }
}


@Composable
fun OrderCharacter(
    name: String,
    value: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = name,
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(Res.font.inter_bold))
        )

        Text(
            text = value,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.inter_regular))
        )
    }
}