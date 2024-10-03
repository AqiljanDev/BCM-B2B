package kz.bcm.b2b.presentation.ui.catalog

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_camera_off
import bcm_b2b.composeapp.generated.resources.ic_cart
import bcm_b2b.composeapp.generated.resources.ic_compare
import bcm_b2b.composeapp.generated.resources.ic_favorite
import bcm_b2b.composeapp.generated.resources.ic_favorite_filled
import bcm_b2b.composeapp.generated.resources.ic_minus
import bcm_b2b.composeapp.generated.resources.inter
import bcm_b2b.composeapp.generated.resources.inter_light
import bcm_b2b.composeapp.generated.resources.inter_medium
import bcm_b2b.composeapp.generated.resources.inter_regular
import bcm_b2b.composeapp.generated.resources.oswald_medium
import bcm_b2b.composeapp.generated.resources.oswald_regular
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kz.bcm.b2b.data.dto.cart.event.PostCartDto
import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.findOneCatalog.CharactersToProducts
import kz.bcm.b2b.domain.data.findOneCatalog.Product
import kz.bcm.b2b.domain.data.findOneCatalog.UserDiscount
import kz.bcm.b2b.domain.data.wishlistAndCompare.GetMini
import kz.bcm.b2b.presentation.other.discountPrice
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorMainOrange
import kz.bcm.b2b.presentation.other.theme.ColorPurpleElectric
import kz.bcm.b2b.presentation.other.theme.ColorVioletElectric
import kz.bcm.b2b.presentation.other.theme.ColorYellowMikado
import kz.bcm.b2b.presentation.other.theme.Url
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProductItem(
    product: Product,
    compareList: List<GetMini>,
    favoriteList: List<GetMini>,
    cartList: List<kz.bcm.b2b.domain.data.cart.mini.Product>,
    discount: List<UserDiscount>,
    clickFavorite: (prodId: String) -> Unit,
    clickCompare: (prodId: String) -> Unit,
    clickCart: (item: PostCart, id: Int) -> Unit,
    clickRoot: (slug: String) -> Unit
) {
    val src = Url.SRC_IMAGE + product.gallery.firstOrNull()?.photo
    val disc = product.discountPrice(discount)

    var stateCompare by remember {
        mutableStateOf(
            compareList.any { it.prodId == product.id1c }
        )
    }

    var stateFavorite by remember {
        mutableStateOf(
            favoriteList.any { it.prodId == product.id1c }
        )
    }

    var stateCart by remember {
        mutableStateOf(
            cartList.find { it.prodId == product.id1c }?.count ?: 0
        )
    }

    LaunchedEffect(compareList) {
        stateCompare = compareList.any { it.prodId == product.id1c }
    }

    LaunchedEffect(favoriteList) {
        stateFavorite = favoriteList.any { it.prodId == product.id1c }
    }


    var isAnimating by remember { mutableStateOf(false) }
    var isAnimatingBounce by remember { mutableStateOf(false) }
    // Анимация масштаба
    val animatedScale by animateFloatAsState(
        targetValue = if (isAnimating) 1.3f else 1f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        finishedListener = {

            if (isAnimating) {
                isAnimating = false
            }

        }
    )

    val bounceScale by animateFloatAsState(
        targetValue = if (isAnimatingBounce) 1.3f else 1f,
        animationSpec = tween(
            durationMillis = 400,
            easing = {
                FastOutLinearInEasing.transform(it)
            }
        ),
        finishedListener = { isAnimatingBounce = false }
    )


    println("Image: $src, Disc: $disc")

    val painter = asyncPainterResource(src)

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {

        Row(
            modifier = Modifier.fillMaxWidth().height(200.dp)
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
                .clickable {
                    clickRoot(product.slug)
                },
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if (product.gallery.isNotEmpty()) {
                    KamelImage(
                        resource = painter,
                        contentDescription = "Photo",
                        modifier = Modifier.size(100.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
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
                Row(
                    modifier = Modifier.padding(bottom = 9.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(
                            if (stateFavorite) Res.drawable.ic_favorite_filled else Res.drawable.ic_favorite
                        ),
                        contentDescription = null,
                        tint = ColorMainGreen,
                        modifier = Modifier
                            .size(27.dp)
                            .scale(animatedScale)
                            .clickable(
                                indication = null,
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }
                            ) {
                                clickFavorite(product.id1c)
                                isAnimating = true
                            }
                    )

                    Icon(
                        painter = painterResource(Res.drawable.ic_compare),
                        contentDescription = null,
                        tint = if (stateCompare) Color.White else ColorMainGreen,
                        modifier = Modifier
                            .size(27.dp)
                            .scale(bounceScale)
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
                                isAnimatingBounce = true
                            }
                    )
                }
            }


            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = product.title,
                        fontSize = 13.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = FontFamily(Font(Res.font.inter_medium))
                    )

                    Text(
                        text = "Арт: ${product.article}",
                        fontSize = 11.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_regular)),
                        color = Color.LightGray
                    )

                    CharacterList(product.charactersToProducts)
                }

                Spacer(Modifier.height(5.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "${formatPrice(disc.price)} ₸",
                            fontSize = 17.sp,
                            fontFamily = FontFamily(Font(Res.font.oswald_medium))
                        )

                        if (disc.discountValue != 0) {
                            Text(
                                text = "${formatPrice(product.price)} ₸",
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(Res.font.oswald_regular)),
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    if (stateCart <= 0) {

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(color = ColorMainGreen)
                                .padding(horizontal = 12.dp, vertical = 9.dp)
                                .clickable {
                                    stateCart = 1

                                    clickCart(
                                        PostCartDto(product.id1c, stateCart),
                                        getIdFromCartMini(cartList, product.id1c)
                                    )
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
                                "В корзину",
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(Res.font.inter_regular)),
                                color = Color.White
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier.widthIn(min = 100.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                "Наличие: ${product.count} шт",
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(Res.font.inter_regular)),
                                color = Color.Gray
                            )

                            Row(
                                modifier = Modifier.widthIn(min = 100.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_minus),
                                    contentDescription = "minus",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(27.dp)
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(color = ColorMainGreen)
                                        .padding(6.dp).clickable(
                                            indication = null,
                                            interactionSource = remember {
                                                MutableInteractionSource()
                                            }
                                        ) {
                                            stateCart--

                                            clickCart(
                                                PostCartDto(
                                                    prodId = product.id1c,
                                                    stateCart
                                                ),
                                                getIdFromCartMini(cartList, product.id1c)
                                            )

                                        }
                                )

                                Text(
                                    text = stateCart.toString(),
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(Res.font.oswald_medium))
                                )

                                Icon(
                                    imageVector = Icons.Sharp.Add,
                                    contentDescription = "plus",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(27.dp)
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(color = ColorMainGreen)
                                        .padding(6.dp)
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember {
                                                MutableInteractionSource()
                                            }
                                        ) {
                                            if (stateCart < product.count) {
                                                stateCart++

                                                clickCart(
                                                    PostCartDto(
                                                        prodId = product.id1c,
                                                        stateCart
                                                    ),
                                                    getIdFromCartMini(cartList, product.id1c)
                                                )
                                            }
                                        }
                                )
                            }
                        }
                    }
                }


            }
        }

        if (disc.discountType != 0) {
            Text(
                text = "${disc.discountValue} ${if (disc.discountType == 1) "%" else "₸"}",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium)),
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (disc.discountType == 1) {
                            Brush.linearGradient(
                                colors = listOf(ColorYellowMikado, ColorMainOrange),
                                start = Offset(x = 0f, y = Float.POSITIVE_INFINITY),
                                end = Offset(x = Float.POSITIVE_INFINITY, y = 0f)
                            )
                        } else
                            Brush.linearGradient(
                                colors = listOf(ColorPurpleElectric, ColorVioletElectric),
                                start = Offset(x = 0f, y = Float.POSITIVE_INFINITY),
                                end = Offset(x = Float.POSITIVE_INFINITY, y = 0f)
                            )

                    ).padding(4.dp)
            )
        }
    }
}


@Composable
fun CharacterList(list: List<CharactersToProducts>, takeCount: Int = 3) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(list.take(takeCount)) { char ->

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "·", fontSize = 12.sp, fontFamily = FontFamily(Font(Res.font.inter)))
                Text(
                    text = "${char.character.title}:",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_light))
                )
                Text(
                    text = char.characterValue.title,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_regular))
                )
            }
        }
    }
}

fun getIdFromCartMini(
    cartMini: List<kz.bcm.b2b.domain.data.cart.mini.Product>,
    prodId: String
): Int {
    return cartMini.find { it.prodId == prodId }?.id ?: 0
}


fun formatPrice(price: Int): String {
    return price.toString().reversed().chunked(3).joinToString(" ").reversed()
}