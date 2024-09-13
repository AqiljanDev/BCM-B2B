package kz.bcm.b2b.presentation.ui.card

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_camera_off
import bcm_b2b.composeapp.generated.resources.ic_cart
import bcm_b2b.composeapp.generated.resources.ic_compare
import bcm_b2b.composeapp.generated.resources.ic_correct
import bcm_b2b.composeapp.generated.resources.ic_favorite
import bcm_b2b.composeapp.generated.resources.ic_favorite_filled
import bcm_b2b.composeapp.generated.resources.ic_minus
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import bcm_b2b.composeapp.generated.resources.inter_regular
import bcm_b2b.composeapp.generated.resources.oswald_bold
import bcm_b2b.composeapp.generated.resources.oswald_medium
import bcm_b2b.composeapp.generated.resources.oswald_regular
import kz.bcm.b2b.data.dto.cart.event.PostCartDto
import kz.bcm.b2b.data.dto.findOneProduct.FindOneProductDto
import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.data.findOneCatalog.Product
import kz.bcm.b2b.domain.data.findOneProduct.FindOneProduct
import kz.bcm.b2b.domain.data.wishlistAndCompare.GetMini
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorMainOrange
import kz.bcm.b2b.presentation.other.theme.ColorMunsell
import kz.bcm.b2b.presentation.other.theme.ColorPlatinum
import kz.bcm.b2b.presentation.ui.catalog.Breadcrumbs
import kz.bcm.b2b.presentation.ui.catalog.ProductItem
import kz.bcm.b2b.presentation.ui.catalog.getIdFromCartMini
import kz.bcm.b2b.presentation.viewmodel.CardViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject


@Composable
fun CardScreen(navController: NavController, slug: String?) {
    val viewModel: CardViewModel = koinInject()

    val stateProduct = viewModel.product.collectAsState()
    val stateFavorite = viewModel.favorite.collectAsState()
    val stateCompare = viewModel.compare.collectAsState()
    val stateCart = viewModel.cart.collectAsState()

    LaunchedEffect(Unit) {
        println("view model product = initializeData, slug: $slug")
        viewModel.getFindOneProduct(slug ?: throw Exception("Slug not passed to CardScreen"))
        viewModel.initializeData()

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Breadcrumbs(
            stateProduct.value.categories.title,
            stateProduct.value.categories.slug,
            stateProduct.value.categories.parentCategory
        ) { slugStr ->
            println("slug: ${stateProduct.value.categories.slug}, name: ${Route.CATALOG.route} vs ${Route.CARD.route}")

            navController.navigate("${Route.CATALOG.route}/$slugStr")
        }

        Text(
            text = stateProduct.value.title,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.inter_bold)),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (stateProduct.value.gallery.isNotEmpty()) {
            ProductImageSlider(
                imageList = stateProduct.value.gallery.map { it.photo },
                discount = stateProduct.value.discount
            )
        } else {
            Image(
                painter = painterResource(Res.drawable.ic_camera_off),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .alpha(0.5f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        MainCharacters(stateProduct.value)

        Text(
            text = stateProduct.value.desc,
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(Res.font.inter_regular)),
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )


        println("stateProduct id1c = ${stateProduct.value.id1c}")
        ProductActionsAndPrice(
            product = stateProduct.value,
            favoriteList = stateFavorite.value,
            compareList = stateCompare.value,
            cartList = stateCart.value.products,
            clickCompare = { prodId -> viewModel.eventCompare(prodId) },
            clickFavorite = { prodId -> viewModel.eventFavorite(prodId) },
            clickCart = { item, id -> viewModel.eventCart(item, id) }
        )

        TabLayout(product = stateProduct.value)

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier.heightIn(max = 2000.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            item {
                Text(
                    text = "С этим товаром также смотрят",
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_bold))
                )
            }

            items(stateProduct.value.other) {

                ProductItem(
                    product = it,
                    compareList = stateCompare.value,
                    favoriteList = stateFavorite.value,
                    cartList = stateCart.value.products,
                    clickFavorite = { prodId -> viewModel.eventFavorite(prodId) },
                    clickCompare = { prodId -> viewModel.eventCompare(prodId) },
                    clickCart = { item, id -> viewModel.eventCart(item, id) },
                    clickRoot = { slug -> navController.navigate("${Route.CARD.route}/$slug") }
                )
            }
        }
    }
}


@Composable
fun MainCharacters(product: FindOneProduct) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            product.brands?.let {
                Text(
                    text = it.title,
                    fontSize = 17.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium)),
                    color = Color.Gray
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = "В наличии:",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_regular)),
                    color = Color.Gray
                )

                Icon(
                    painter = painterResource(Res.drawable.ic_correct),
                    contentDescription = "correct",
                    tint = ColorMainGreen,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = "${product.count} шт",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_regular)),
                    color = Color.Gray
                )
            }

            Text(
                text = "арт. ${product.article}",
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(Res.font.inter_regular)),
                color = Color.Gray
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )
    }
}


@Composable
fun ProductActionsAndPrice(
    product: FindOneProduct,
    favoriteList: List<GetMini>,
    compareList: List<GetMini>,
    cartList: List<kz.bcm.b2b.domain.data.cart.mini.Product>,
    clickFavorite: (prodId: String) -> Unit,
    clickCompare: (prodId: String) -> Unit,
    clickCart: (item: PostCart, id: Int) -> Unit
) {

    println("prod actions = slug: ${product.slug}, id1c: ${product.id1c} cart: $cartList, compare: $compareList, favorite: $favoriteList")

    var stateCompare by remember(compareList) {
        mutableStateOf(compareList.any { it.prodId == product.id1c })
    }

    var stateFavorite by remember(favoriteList) {
        mutableStateOf(favoriteList.any { it.prodId == product.id1c })
    }

    val stateCart = remember(cartList) {
        mutableStateOf(cartList.find { it.prodId == product.id1c }?.count ?: 0)
    }


    LaunchedEffect(compareList, product.id1c) {
        stateCompare = compareList.any { it.prodId == product.id1c }
    }

    LaunchedEffect(favoriteList, product.id1c) {
        stateFavorite = favoriteList.any { it.prodId == product.id1c }
    }

    LaunchedEffect(cartList, product.id1c) {
        stateCart.value = cartList.find { it.prodId == product.id1c }?.count ?: 0
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            product.discount?.let {
                Text(
                    text = it.value.toString(),
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(Res.font.oswald_regular)),
                    color = Color.DarkGray
                )
            }

            Text(
                text = "${product.price} ₸",
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(Res.font.oswald_bold))
            )
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_compare),
                contentDescription = "compare",
                tint = if (stateCompare) Color.White else ColorMainGreen,
                modifier = Modifier
                    .size(27.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        if (stateCompare) ColorMainGreen else Color.White
                    )
                    .clickable(
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        }
                    ) {
                        clickCompare(product.id1c)
                    }
            )

            if (stateCart.value < 1) {

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = ColorMainGreen)
                        .padding(horizontal = 45.dp, vertical = 9.dp)
                        .clickable {
                            stateCart.value = 1

                            clickCart(

                                PostCartDto(
                                    product.id1c,
                                    stateCart.value
                                ),
                                getIdFromCartMini(cartList, product.id1c)
                            )
                        },
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_cart),
                        contentDescription = "cart",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )

                    Text(
                        text = "В корзину",
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_regular)),
                        color = Color.White
                    )
                }
            } else {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_minus),
                        contentDescription = "minus",
                        tint = Color.White,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(color = ColorMainGreen)
                            .padding(9.dp).clickable(
                                indication = null,
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }
                            ) {
                                stateCart.value--

                                clickCart(
                                    PostCartDto(
                                        prodId = product.id1c,
                                        stateCart.value
                                    ),
                                    getIdFromCartMini(cartList, product.id1c)
                                )

                            }
                    )


                    Text(
                        text = stateCart.value.toString(),
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(Res.font.oswald_medium))
                    )

                    Icon(
                        imageVector = Icons.Sharp.Add,
                        contentDescription = "plus",
                        tint = Color.White,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(color = ColorMainGreen)
                            .padding(9.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }
                            ) {
                                if (stateCart.value < product.count) {
                                    stateCart.value++

                                    clickCart(
                                        PostCartDto(
                                            prodId = product.id1c,
                                            stateCart.value
                                        ),
                                        getIdFromCartMini(cartList, product.id1c)
                                    )
                                }
                            }
                    )
                }
            }

            Icon(
                painter = painterResource(
                    if (stateFavorite) Res.drawable.ic_favorite_filled else Res.drawable.ic_favorite
                ),
                contentDescription = "favorite",
                tint = ColorMainGreen,
                modifier = Modifier.size(27.dp).clickable(
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }
                ) {
                    clickFavorite(product.id1c)
                }
            )
        }
    }
}


@Composable
fun TabLayout(product: FindOneProduct) {
    val tabs = mutableListOf("Характеристики", "Описание")

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val density = LocalDensity.current
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }

    Column {

        TabRow(
            modifier = Modifier,
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.customTabIndicatorOffset(
                        currentTabPosition = tabPositions[selectedTabIndex],
                        tabWidth = tabWidths[selectedTabIndex]
                    ),
                    color = ColorMainOrange
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                val interactionSource = remember { MutableInteractionSource() }

                // Применяем Modifier.clickable с indication
                val clickableModifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    selectedTabIndex = index
                }

                Tab(
                    modifier = clickableModifier
                        .background(Color.Transparent),
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    interactionSource = interactionSource,
                    text = {
                        Text(
                            text = title,
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(Res.font.oswald_regular)),
                            color = if (selectedTabIndex == index) ColorMainOrange
                            else Color.Gray,
                            onTextLayout = { textLayoutResult ->
                                tabWidths[index] =
                                    with(density) { textLayoutResult.size.width.toDp() }
                            }
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        when (selectedTabIndex) {


            0 -> {

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Характеристики",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_medium))
                    )

                    ProductCharacters(product)
                }
            }

            1 -> {

                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Text(
                        text = "Описание",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_bold))
                    )

                    Text(
                        text = product.desc,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_regular))
                    )
                }

            }
        }
    }
}

@Composable
private fun ProductCharacters(product: FindOneProduct) {
    LazyColumn(
        modifier = Modifier.heightIn(max = 1500.dp)
    ) {
        itemsIndexed(product.charactersToProducts) { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(7.dp))
                    .background(
                        if (index % 2 == 0) ColorMunsell else Color.Transparent
                    )
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.character.title,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_regular))
                )

                Text(
                    item.characterValue.title,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium))
                )
            }
        }
    }
}


fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}







