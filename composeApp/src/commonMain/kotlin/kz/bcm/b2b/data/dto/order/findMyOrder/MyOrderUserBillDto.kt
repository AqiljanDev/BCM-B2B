package kz.bcm.b2b.data.dto.order.findMyOrder

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.order.findMyOrder.MyOrderUserBill

@Serializable
data class MyOrderUserBillDto(
    override val id: Int = 0,
    override val bank: String = "",
    override val code: String = "",
    override val kbe: String = "",
    override val status: Int = 0,
    override val usersId: Int = 0
) : MyOrderUserBill
