package kz.bcm.b2b.presentation.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_eye
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import bcm_b2b.composeapp.generated.resources.inter_regular
import bcm_b2b.composeapp.generated.resources.oswald_medium
import kz.bcm.b2b.domain.data.findOneCatalog.UserDiscount
import kz.bcm.b2b.domain.data.order.findMyOrder.MyOrder
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorMainOrange
import kz.bcm.b2b.presentation.other.theme.ColorMunsell
import kz.bcm.b2b.presentation.other.theme.ColorPlatinum
import kz.bcm.b2b.presentation.other.theme.ColorPurpleElectric
import kz.bcm.b2b.presentation.other.theme.ColorVioletElectric
import kz.bcm.b2b.presentation.other.theme.ColorYellowMikado
import kz.bcm.b2b.presentation.ui.catalog.formatPrice
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrdersItem(
    orders: List<MyOrder>,
    discounts: List<UserDiscount>,
    clickDiscount: (String) -> Unit,
    clickEye: (id: Int) -> Unit
) {
    val outScroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .verticalScroll(outScroll),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Заказы",
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(Res.font.inter_bold))
        )

        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(ColorMunsell))

        if (discounts.isNotEmpty()) {
            Text(
                text = "Мои скидки",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium))
            )
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            discounts.forEach { discount ->
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(ColorYellowMikado, ColorMainOrange),
                                start = Offset(x = 0f, y = Float.POSITIVE_INFINITY),
                                end = Offset(x = Float.POSITIVE_INFINITY, y = 0f)
                            )
                        )
                        .clickable {
                            clickDiscount(discount.userCategory?.slug ?: "")
                        }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${discount.value}%",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_medium)),
                        color = Color.White
                    )

                    Text(
                        text = discount.userCategory?.title ?: "",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_medium)),
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "История",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium))
        )

        LazyColumn(
            modifier = Modifier.heightIn(max = 50_000.dp)
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = orders) { order ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(70.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(ColorPlatinum)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(
                        modifier = Modifier
                            .height(70.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "#${order.id}",
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(Res.font.inter_bold))
                        )

                        Text(
                            text = order.date,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(Res.font.inter_regular))
                        )
                    }

                    Column(
                        modifier = Modifier.height(70.dp),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = order.orderStatus.name,
                                fontSize = 13.sp,
                                fontFamily = FontFamily(Font(Res.font.inter_regular))
                            )

                            Icon(
                                painter = painterResource(Res.drawable.ic_eye),
                                contentDescription = "eye",
                                tint = ColorPlatinum,
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(ColorMainGreen)
                                    .padding(4.dp)
                                    .clickable {
                                        clickEye(order.id)
                                    }
                            )
                        }

                        Text(
                            text = "${formatPrice(order.products.sumOf { it.count * it.price })} ₸",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(Res.font.oswald_medium))
                        )
                    }

                }
            }
        }
    }
}