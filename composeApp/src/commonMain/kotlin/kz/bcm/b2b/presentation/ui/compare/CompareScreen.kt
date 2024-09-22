package kz.bcm.b2b.presentation.ui.compare

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_back
import bcm_b2b.composeapp.generated.resources.ic_camera_off
import bcm_b2b.composeapp.generated.resources.ic_cart
import bcm_b2b.composeapp.generated.resources.ic_delete
import bcm_b2b.composeapp.generated.resources.ic_info_outline
import bcm_b2b.composeapp.generated.resources.ic_left_arrow
import bcm_b2b.composeapp.generated.resources.ic_minus
import bcm_b2b.composeapp.generated.resources.ic_right_arrow
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import bcm_b2b.composeapp.generated.resources.inter_regular
import bcm_b2b.composeapp.generated.resources.oswald_bold
import bcm_b2b.composeapp.generated.resources.oswald_medium
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import kz.bcm.b2b.data.dto.cart.event.PostCartDto
import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.mini.Product
import kz.bcm.b2b.domain.data.compare.CharactersToCompare
import kz.bcm.b2b.domain.data.compare.ProductNested
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.theme.ColorBlueFlower
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.Url
import kz.bcm.b2b.presentation.ui.catalog.getIdFromCartMini
import kz.bcm.b2b.presentation.viewmodel.CompareViewModel
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject


@Composable
fun CompareScreen(navController: NavController) {
    val viewModel: CompareViewModel = koinInject()

    val stateScroll = rememberScrollState()
    val productListState = rememberLazyListState()
    val characterListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val stateProduct = viewModel.product.collectAsState()
    val stateCart = viewModel.cart.collectAsState()
    val stateUserDiscount = viewModel.userDiscount.collectAsState()

    var stateActive by remember {
        mutableStateOf(CompareItems.CATEGORIES)
    }

    var stateCurrCategory by remember {
        mutableStateOf("Каталог")
    }

    LaunchedEffect(Unit) {
        viewModel.getProduct()
        viewModel.getCart()
    }


    LaunchedEffect(productListState.firstVisibleItemIndex) {
        if (productListState.firstVisibleItemIndex != characterListState.firstVisibleItemIndex) {
            characterListState.animateScrollToItem(productListState.firstVisibleItemIndex)
        }
    }

    LaunchedEffect(characterListState.firstVisibleItemIndex) {
        if (characterListState.firstVisibleItemIndex != productListState.firstVisibleItemIndex) {
            productListState.animateScrollToItem(characterListState.firstVisibleItemIndex)
        }
    }


    LaunchedEffect(stateProduct) {
        if (
            stateProduct.value.products.none {
                (it.product.categories.parentCategory?.title ?: "Каталог") == stateCurrCategory
            }
        ) stateActive = CompareItems.CATEGORIES
    }


    val prodSize = stateProduct.value.products.filter {
        (it.product.categories.parentCategory?.title ?: "Каталог") == stateCurrCategory
    }.size


    AnimatedVisibility(visible = stateActive == CompareItems.CATEGORIES) {

        val categoriesMap = stateProduct.value.products
            .map { it.product.categories.parentCategory?.title ?: "Каталог" }
            .groupingBy { it }
            .eachCount()

        println("result listCategory: $categoriesMap")

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(18.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        text = "В сравнении:",
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_bold))
                    )
                }
            }

            items(categoriesMap.keys.toList()) { key ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = key,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(Res.font.inter_regular)),
                            color = ColorBlueFlower,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable {
                                stateActive = CompareItems.LIST
                                stateCurrCategory = key
                            }
                        )

                        Text(
                            text = categoriesMap[key].toString(),
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(Res.font.inter_medium)),
                            color = Color.Gray
                        )
                    }

                    Icon(
                        painter = painterResource(Res.drawable.ic_delete),
                        contentDescription = "delete",
                        tint = Color.Gray,
                        modifier = Modifier.size(25.dp)
                            .clickable {
                                val listProdId = stateProduct.value.products
                                    .filter {
                                        (it.product.categories.parentCategory?.title
                                            ?: "Каталог") == key
                                    }
                                    .map { it.product.id1c }

                                listProdId.forEach { id ->
                                    viewModel.deleteCompare(id)
                                }
                            }
                    )


                }
            }

        }

    }


    AnimatedVisibility(visible = stateActive == CompareItems.LIST) {

        Column(
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(stateScroll),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),

            ) {

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(Res.drawable.ic_back),
                    contentDescription = "back",
                    modifier = Modifier.size(22.dp)
                        .clickable {
                            stateActive = CompareItems.CATEGORIES
                        }
                )

                Text(
                    text = "Сравнение",
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_bold))
                )

                Icon(
                    painter = painterResource(Res.drawable.ic_back),
                    contentDescription = "back",
                    modifier = Modifier.size(16.dp).alpha(0f)
                )
            }


            Column {
                Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_left_arrow),
                        contentDescription = "left",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                coroutineScope.launch {
                                    val fv = productListState.firstVisibleItemIndex
                                    if (fv > 0) {
                                        productListState.animateScrollToItem(fv - 1) // Прокрутить на 1 элемент назад
                                        characterListState.animateScrollToItem(fv - 1)
                                    }
                                }
                            }
                    )

                    Text(
                        text = "${productListState.firstVisibleItemIndex + 1}-${if (productListState.firstVisibleItemIndex + 2 > prodSize) prodSize else productListState.firstVisibleItemIndex + 2} / $prodSize",
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_medium)),
                        color = Color.Gray
                    )


                    Icon(
                        painter = painterResource(Res.drawable.ic_right_arrow),
                        contentDescription = "right",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable(
                                indication = null,
                                interactionSource = MutableInteractionSource()
                            ) {
                                coroutineScope.launch {
                                    val fv = productListState.firstVisibleItemIndex
                                    if (fv < prodSize - 1) {
                                        productListState.animateScrollToItem(fv + 1) // Прокрутить на 1 элемент вперед
                                        characterListState.animateScrollToItem(fv + 1)
                                    }
                                }
                            }
                    )
                }

                Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray))
            }



            LazyRow(
                state = productListState,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {

                items(stateProduct.value.products.filter {
                    (it.product.categories.parentCategory?.title ?: "Каталог") == stateCurrCategory
                }) { prod ->
                    println("top = prod: $prod = size ${stateProduct.value.products.size}")

                    CompareProduct(
                        product = prod.product,
                        cartList = stateCart.value.products,
                        deleteProduct = { prodId -> viewModel.deleteCompare(prodId) },
                        updateCart = { item -> viewModel.updateCart(item) },
                        deleteCart = { prodId -> viewModel.deleteCart(prodId) },
                        modifier = Modifier.fillParentMaxWidth(0.485f)
                    ) { slug ->
                        navController.navigate("${Route.CARD.route}/$slug")
                    }
                }
            }

            if (stateProduct.value.characters.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "Характеристики",
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_bold))
                    )
                }
            }


            LazyRow(
                state = characterListState,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {

                items(stateProduct.value.products.filter {
                    (it.product.categories.parentCategory?.title ?: "Каталог") == stateCurrCategory
                }) { prod ->
                    println("bottom = prod: $prod = size ${stateProduct.value.products.size}")

                    CompareCharacter(
                        chars = stateProduct.value.characters,
                        charValues = prod.product.characters,
                        modifier = Modifier.fillParentMaxWidth(0.485f),
                        stateScroll = stateScroll
                    )

                }
            }


        }
    }
}


@Composable
fun CompareProduct(
    product: ProductNested,
    cartList: List<Product>,
    deleteProduct: (prodId: String) -> Unit,
    updateCart: (item: PostCart) -> Unit,
    deleteCart: (prodId: Int) -> Unit,
    modifier: Modifier = Modifier,
    clickRoot: (slug: String) -> Unit
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopEnd
    ) {
        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (product.photo != null) {
                KamelImage(
                    resource = asyncPainterResource(Url.SRC_IMAGE + product.photo),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable {
                            clickRoot(product.slug)
                        }
                )
            } else {
                Image(
                    painter = painterResource(Res.drawable.ic_camera_off),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(1f)
                        .alpha(0.4f)
                        .clickable {
                            clickRoot(product.slug)
                        }
                )
            }

            Text(
                text = product.title,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.inter_bold)),
                minLines = 4,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "${(product.price * 1.2).toInt()} ₸",
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(Res.font.oswald_medium)),
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )

                Text(
                    text = "${product.price} ₸",
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(Res.font.oswald_bold))
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                contentAlignment = Alignment.Center
            ) {

                CartButtonAndCount(
                    product = product,
                    cartList = cartList,
                    updateCart = updateCart,
                    deleteCart = deleteCart
                )
            }
        }

        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = null,
            modifier = Modifier
                .size(28.dp)
                .alpha(0.7f)
                .clickable {
                    deleteProduct(product.id1c)
                }
        )
    }

}


@Composable
fun CompareCharacter(
    chars: List<CharactersToCompare>,
    charValues: List<String>,
    modifier: Modifier,
    stateScroll: ScrollState
) {

    LaunchedEffect(chars, charValues) {
        println("Chars: $chars")
        println("Char Values: $charValues")
    }

    LazyColumn(
        modifier = modifier.heightIn(max = 10_000.dp)
            .nestedScroll(
                object : NestedScrollConnection {

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
                }
            ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        itemsIndexed(chars) { index, char ->

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_info_outline),
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = char.title,
                        fontSize = 11.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_regular)),
                        color = Color.LightGray
                    )
                }

                Text(
                    text = charValues[index],
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium))
                )
            }

        }
    }
}


@Composable
private fun CartButtonAndCount(
    product: ProductNested,
    cartList: List<Product>,
    updateCart: (PostCart) -> Unit,
    deleteCart: (prodId: Int) -> Unit
) {

    val stateCart = remember {
        mutableStateOf(
            cartList.find { it.prodId == product.id1c }?.count ?: 0
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {

        Text(
            text = "Наличие: ${product.count} шт",
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium)),
            color = Color.Gray,
            modifier = Modifier.alpha(
                if (stateCart.value < 1) 0f else 1f
            )
        )

        if (stateCart.value < 1) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = ColorMainGreen)
                    .padding(horizontal = 15.dp, vertical = 9.dp)
                    .clickable {
                        stateCart.value = 1

                        updateCart(
                            PostCartDto(
                                product.id1c,
                                stateCart.value
                            )
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
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

                            if (stateCart.value > 0) {
                                updateCart(
                                    PostCartDto(
                                        prodId = product.id1c,
                                        stateCart.value
                                    )
                                )
                            } else {
                                deleteCart(
                                    getIdFromCartMini(cartList, product.id1c)
                                )
                            }

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

                                updateCart(
                                    PostCartDto(
                                        prodId = product.id1c,
                                        stateCart.value
                                    )
                                )
                            }
                        }
                )
            }
        }
    }
}


private enum class CompareItems {
    CATEGORIES,
    LIST
}