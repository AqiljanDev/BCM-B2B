package kz.bcm.b2b.data.dto.order.findMyOrder

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.order.findMyOrder.MyOrderStatus

@Serializable
data class MyOrderStatusDto(
    override val id: Int = 0,
    override val name: String = "",
    override val default: Int = 0,
    override val endStatus: Int = 0
) : MyOrderStatus
