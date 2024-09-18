package kz.bcm.b2b.data.dto.order.findMyOrder

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.order.findMyOrder.MyOrderProduct


@Serializable
data class MyOrderProductDto(
    override val id: Int = 0,
    override val price: Int = 0,
    override val count: Int = 0
) : MyOrderProduct
