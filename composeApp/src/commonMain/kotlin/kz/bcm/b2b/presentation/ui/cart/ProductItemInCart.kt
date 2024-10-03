package kz.bcm.b2b.presentation.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_camera_off
import bcm_b2b.composeapp.generated.resources.inter_regular
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.findOneCatalog.Product
import kz.bcm.b2b.presentation.other.theme.Url
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import bcm_b2b.composeapp.generated.resources.ic_minus
import bcm_b2b.composeapp.generated.resources.inter_medium
import bcm_b2b.composeapp.generated.resources.oswald_medium
import bcm_b2b.composeapp.generated.resources.oswald_regular
import kz.bcm.b2b.data.dto.cart.event.PostCartDto
import kz.bcm.b2b.domain.data.findOneCatalog.UserDiscount
import kz.bcm.b2b.presentation.other.discountPrice
import kz.bcm.b2b.presentation.other.theme.ColorDarkRed
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorMainOrange
import kz.bcm.b2b.presentation.other.theme.ColorPurpleElectric
import kz.bcm.b2b.presentation.other.theme.ColorVioletElectric
import kz.bcm.b2b.presentation.other.theme.ColorYellowMikado
import kz.bcm.b2b.presentation.ui.catalog.formatPrice
import kz.bcm.b2b.presentation.ui.catalog.getIdFromCartMini


@Composable
fun ProductItemInCart(
    product: Product,
    cartList: List<kz.bcm.b2b.domain.data.cart.mini.Product>,
    clickEvent: (item: PostCart) -> Unit,
    clickDelete: (id: Int) -> Unit,
    clickRoot: (slug: String) -> Unit,
    discount: List<UserDiscount>
) {
    val desc = product.discountPrice(discount)

    var stateCart by remember {
        mutableStateOf(
            cartList.find { it.prodId == product.id1c }?.count ?: 0
        )
    }

    LaunchedEffect(cartList, product) {
        stateCart = cartList.find { it.prodId == product.id1c }?.count ?: 0
    }

    Box(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier.fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 16.dp)
                .clickable {
                    clickRoot(product.slug)
                },
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Box(
                modifier = Modifier.fillMaxWidth(0.2f),
                contentAlignment = Alignment.Center
            ) {
                if (product.gallery.isNotEmpty()) {
                    KamelImage(
                        resource = asyncPainterResource(Url.SRC_IMAGE + product.gallery.firstOrNull()?.photo),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().aspectRatio(1f)
                    )
                } else {

                    Image(
                        painter = painterResource(Res.drawable.ic_camera_off),
                        contentDescription = "photo missing",
                        modifier = Modifier.fillMaxSize().aspectRatio(1f)
                    )
                }
            }


            Column(
                modifier = Modifier.fillMaxWidth(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = product.title,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_medium)),
                        modifier = Modifier.weight(0.9f)
                    )

                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "clear",
                        tint = ColorDarkRed,
                        modifier = Modifier.weight(0.1f)
                            .clickable {
                                val id = getIdFromCartMini(cartList, product.id1c)
                                println("click delete, id: $id, id1c: ${product.id1c} cartList: $cartList")

                                clickDelete(
                                    id
                                )
                            }
                    )
                }

                Text(
                    text = "${formatPrice(desc.price)} ₸",
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(Res.font.oswald_regular)),
                    color = Color.Gray
                )


                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Наличие:\n${formatPrice(product.count)} шт",
                        fontSize = 11.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_regular)),
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Row(
                        modifier = Modifier.widthIn(min = 100.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_minus),
                            contentDescription = "minus",
                            tint = Color.White,
                            modifier = Modifier
                                .size(32.dp)
                                .alpha(
                                    if (stateCart == 1) 0.5f else 1f
                                )
                                .clip(RoundedCornerShape(7.dp))
                                .background(color = ColorMainGreen)
                                .padding(8.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }
                                ) {
                                    if (stateCart > 1) {
                                        stateCart--

                                        clickEvent(
                                            PostCartDto(
                                                prodId = product.id1c,
                                                stateCart
                                            )
                                        )
                                    }
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
                                .size(32.dp)
                                .alpha(
                                    if (stateCart == product.count) 0.5f else 1f
                                )
                                .clip(RoundedCornerShape(7.dp))
                                .background(color = ColorMainGreen)
                                .padding(8.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }
                                ) {
                                    if (stateCart < product.count) {
                                        stateCart++

                                        clickEvent(
                                            PostCartDto(
                                                prodId = product.id1c,
                                                stateCart
                                            )
                                        )
                                    }
                                }
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "${formatPrice(desc.price * stateCart)} ₸",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(Res.font.oswald_medium))
                        )

                        if (desc.discountType != 0) {
                            Text(
                                text = "${formatPrice(product.price * stateCart)} ₸",
                                fontSize = 9.sp,
                                fontFamily = FontFamily(Font(Res.font.oswald_regular)),
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }

        if (desc.discountType != 0) {
            Text(
                text = "${desc.discountValue} " + if (desc.discountType == 1) "%" else "₸",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium)),
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (desc.discountType == 1) {
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





