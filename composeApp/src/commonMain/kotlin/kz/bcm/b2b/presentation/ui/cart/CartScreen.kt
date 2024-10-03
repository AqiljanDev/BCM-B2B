package kz.bcm.b2b.presentation.ui.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import bcm_b2b.composeapp.generated.resources.inter_regular
import bcm_b2b.composeapp.generated.resources.oswald_medium
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kz.bcm.b2b.data.dto.order.orders.PostOrdersDto
import kz.bcm.b2b.domain.data.bill.BillMy
import kz.bcm.b2b.domain.data.cart.full.CartFullProduct
import kz.bcm.b2b.domain.data.findOneCatalog.UserDiscount
import kz.bcm.b2b.domain.data.order.orders.PostOrders
import kz.bcm.b2b.presentation.other.data.ProductBasketCreate
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.discountPrice
import kz.bcm.b2b.presentation.other.theme.ColorGreyDavy
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.ui.catalog.formatPrice
import kz.bcm.b2b.presentation.viewmodel.CartViewModel
import org.jetbrains.compose.resources.Font
import org.koin.compose.koinInject


@Composable
fun CartScreen(navController: NavController) {
    val viewModel: CartViewModel = koinInject()
    val stateScroll = rememberScrollState()

    val stateProduct = viewModel.cartProduct.collectAsState()
    val stateCartMini = viewModel.cartMini.collectAsState()
    val stateBillMy = viewModel.billMy.collectAsState()
    val stateOrderDetails = viewModel.orderDetails.collectAsState()
    val stateDiscount = viewModel.discount.collectAsState()

    var stateTitle by remember {
        mutableStateOf("Ваша корзина пуста")
    }

    LaunchedEffect(Unit) {
        viewModel.getCartFull()
        viewModel.getBillMy()
        viewModel.getUserDiscount()
    }

    LaunchedEffect(stateOrderDetails.value) {
        if (stateOrderDetails.value.id != 0) stateTitle =
            "Заказ #${stateOrderDetails.value.id} успешно оформлен!"
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (stateProduct.value.isNotEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp)
                    .verticalScroll(stateScroll)
            ) {

                Spacer(modifier = Modifier.height(13.dp))

                Text(
                    text = "Корзина",
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_bold))
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                        .heightIn(max = 15_000.dp)
                        .background(Color.LightGray)
                        .nestedScroll(connection = object : NestedScrollConnection {
                            override fun onPreScroll(
                                available: Offset,
                                source: NestedScrollSource
                            ): Offset {
                                if (stateScroll.canScrollForward && available.y < 0) {
                                    val consumed = stateScroll.dispatchRawDelta(-available.y)
                                    return Offset(x = 0f, y = -consumed)
                                }
                                return Offset.Zero
                            }
                        }),
                    verticalArrangement = Arrangement.spacedBy(0.8.dp),
                ) {

                    items(items = stateProduct.value) { prod ->

                        ProductItemInCart(
                            product = prod.product,
                            cartList = stateCartMini.value.products,
                            discount = stateDiscount.value,
                            clickEvent = { item -> viewModel.eventCart(item) },
                            clickDelete = { id -> viewModel.deleteCart(id) },
                            clickRoot = { slug -> navController.navigate("${Route.CARD.route}/$slug") }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(13.dp))
                println("cartscreen = ${stateProduct.value}")

                FormFieldsCard(
                    product = stateProduct.value,
                    totalPrice = stateProduct.value.sumOf {
                        it.count * it.product.discountPrice(
                            stateDiscount.value
                        ).price
                    },
                    bill = stateBillMy.value,
                    discountList = stateDiscount.value
                ) { order ->
                    viewModel.postOrders(postOrder = order)
                }
            }

        } else {

            Text(
                text = stateTitle,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(Res.font.inter_regular))
            )
        }
    }
}

@Composable
private fun FormFieldsCard(
    product: List<CartFullProduct>,
    totalPrice: Int,
    bill: List<BillMy>,
    discountList: List<UserDiscount>,
    clickDesign: (order: PostOrders) -> Unit
) {
    val items = listOf("Самовывоз", "Доставка")
    val itemsBills = listOf("Выберете расчетный счет") + bill.map { it.code }

    val stateSelectedDelivery = remember {
        mutableStateOf("Самовывоз")
    }

    val stateSelectedBills = remember {
        mutableStateOf(itemsBills.first())
    }

    val stateAddress = remember {
        mutableStateOf("")
    }

    val stateComment = remember {
        mutableStateOf("")
    }

    Card(
        modifier = Modifier.fillMaxWidth()
            .fillMaxWidth()
            .padding(8.dp) // Внешний отступ вокруг Card
            .shadow(4.dp, shape = RoundedCornerShape(8.dp)) // Добавляем тень
            .clip(RoundedCornerShape(8.dp)) // Скругление углов
            .background(Color.White) // Фоновый цвет
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            var stateError by remember {
                mutableStateOf(false)
            }

            DeliveryMethod(items, stateSelectedDelivery)

            if (stateSelectedDelivery.value == items[1]) {
                Address(stateAddress)
            }

            CurrentAccount(itemsBills, stateSelectedBills)
            Comment(stateComment)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Итого:    ${formatPrice(totalPrice)} ₸",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.oswald_medium))
                )


                Text(
                    text = "Оформить заказ",
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium)),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(ColorMainGreen)
                        .padding(10.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            if (stateSelectedBills.value != itemsBills.first()) {
                                val deliveryId =
                                    if (stateSelectedDelivery.value == items[0]) 1 else 2
                                val billsId =
                                    bill.find { it.bank == stateSelectedBills.value }?.usersId
                                        ?: return@clickable

                                val productBasketList = product.map { prod ->
                                    val disc = prod.product.discountPrice(discountList)

                                    ProductBasketCreate(
                                        id = prod.id,
                                        c = prod.count,
                                        p = prod.product.price,
                                        d = when (disc.discountType) {
                                            1 -> "${disc.price} %"
                                            2 -> "${disc.price} ₸"
                                            else -> "Нет"
                                        }
                                    )
                                }


                                val productJson = Json.encodeToString(productBasketList)

                                println("billsId = ${billsId}, deliveryId: ${deliveryId}, address: ${stateAddress.value} comment: ${stateComment.value}, products: $productJson")

                                val postOrders = PostOrdersDto(
                                    address = stateAddress.value.ifEmpty { " " },
                                    commentUser = stateComment.value.ifEmpty { " " },
                                    userBillId = billsId,
                                    deliverId = deliveryId,
                                    discount = "",
                                    products = productJson
                                )

                                clickDesign(postOrders)

                                stateError = false
                            } else {
                                stateError = true
                            }
                        }
                )

                if (stateError) {
                    Text(
                        text = "Выберите расчетный счет",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_regular)),
                        color = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun Address(stateAddress: MutableState<String>) {

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = "Способ доставки",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.oswald_medium))
        )

        BasicTextField(
            value = stateAddress.value,
            onValueChange = { stateAddress.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 0.8.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
                .heightIn(min = 30.dp),
            textStyle = TextStyle(fontSize = 13.sp, fontFamily = FontFamily.Default)
        )
    }
}

@Composable
private fun DeliveryMethod(
    methods: List<String>,
    selectedItem: MutableState<String>
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = "Способ доставки",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.oswald_medium))
        )

        DropDownMain(items = methods, selectedItem = selectedItem)
    }
}


@Composable
private fun CurrentAccount(
    bills: List<String>,
    selectedItem: MutableState<String>
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = "Расчетный счет",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.oswald_medium))
        )

        DropDownMain(items = bills, selectedItem = selectedItem)
    }
}


@Composable
fun Comment(
    stateComment: MutableState<String>
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = "Комментарий",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.oswald_medium))
        )

        BasicTextField(
            value = stateComment.value,
            onValueChange = { stateComment.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 0.8.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
                .heightIn(min = 60.dp),
            textStyle = TextStyle(fontSize = 13.sp, fontFamily = FontFamily.Default)
        )
    }
}


@Composable
fun DropDownMain(
    items: List<String>,
    selectedItem: MutableState<String>
) {
    var isMenuExpanded by remember {
        mutableStateOf(false)
    }

    Column {
        Text(
            text = selectedItem.value,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium)),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 0.8.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(12.dp)
                .clickable {
                    isMenuExpanded = true
                }
        )

        Box(
            modifier = Modifier.fillMaxWidth() // Убирает избыточную ширину
        ) {

            // DropdownMenu, который открывается при нажатии на кнопку
            DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false },
                modifier = Modifier.fillMaxWidth(0.9f).background(ColorGreyDavy)
            ) {
                // Элементы меню
                items.forEach { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp)
                            .clickable {
                                selectedItem.value = item
                                isMenuExpanded = false // Закрываем меню после выбора
                            }
                    ) {
                        Text(
                            text = item,
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(Res.font.inter_medium)),
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}